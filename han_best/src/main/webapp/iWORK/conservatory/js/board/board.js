var iWORK = iWORK ? iWORK : {};
iWORK.Board = iWORK.Board ? iWORK.Board : {};

iWORK.Board.bindCaptcha = function() {
	create();
	function create() {
		$("#captcha img").attr("src", "/GetCaptcha.do?" + Math.random());
	}
	$("#refreshBtn").click(function(e) {
		e.preventDefault();
		create();
	});
};

iWORK.Board.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/BoardList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.Board.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.Board.goList();
	});
};

iWORK.Board.bindNewSearchLink = function() {
	$('#searchBy').change(function(event){
		$('#curSearchForm input[name=searchBy]').attr('value', $('#searchBy').val());
	});
	
	$('[data-itsp-new-search-link]').click(function(event) {
		if (!itsp.formValidator.validate($('#newSearchForm'))) {
			return;
		}
		
		$('#curSearchForm input[name=searchValue]').attr('value', $('#searchValue').val());
		iWORK.Board.goList();
	});
};

iWORK.Board.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curPage = me.attr('data-itsp-curPage');	
		$('#curPageForm input[name=pageNum]').attr('value', pageNum);
		$('#curPageForm input[name=curPage]').attr('value', curPage);
		iWORK.Board.goList();
	});
	
	$('#curPage').change(function(event){
		$('#curPageForm input[name=curPage]').attr('value', $('#curPage').val());
		iWORK.Board.goList();
	});
};

//등록 및 수정 화면
iWORK.Board.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/BoardEdit.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.Board.bindViewLink = function() {
	$('[data-itsp-view-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-view-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/BoardView.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.Board.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}
		
		if (!confirm("저장 하시겠습니까?")) {
			return;
		}
		
		itsp.ajax.showLoading();
		if($("#contents").length) {
			itsp.com.getHtmlEditorData('contents');
		}
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.Board.goList();
			} else {
				alert(data.header.message);
			}
		});
	};
	
	itsp.com.blockFormEnterKey(form, function() {
		// doSave();
	});
	
	$('[data-itsp-save-link]').click(function(event) {
		doSave();
	});
};


iWORK.Board.bindDeleteLink = function() {
	$('[data-itsp-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-delete-link');
		var isOk = true;
		
		if(seq == "chkBox"){
			seq = "";
			if($('input[name=chkBoxs]:checked').length ==0){
				alert("테이터를 선택해 주세요.");
				isOk = false;
				return false;
			}
			
			$('input[name=chkBoxs]:checked').each(function(){
				seq += $(this).val() + ",";
			});
		}
		
		if(!isOk) return;
		
		if (!confirm("삭제 하시겠습니까?\n삭제 후 복구가 불가능합니다.")) {
			return;
		}		
		
		itsp.ajax.doPostJSON('/Board/Record/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.Board.goList();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
	
	$('[data-itsp-use-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-use-link');
		var isOk = true;
		
		if(seq == "chkBox"){
			seq = "";
			if($('input[name=chkBoxs]:checked').length ==0){
				alert("데이터를 선택해 주세요.");
				isOk = false;
				return false;
			}
			
			$('input[name=chkBoxs]:checked').each(function(){
				seq += $(this).val() + ",";
			});
		}
		
		if(!isOk) return;
		if (!confirm("삭제 하시겠습니까?")) {
			return;
		}
		itsp.ajax.doPostJSON('/Board/Record/useProcData', { seq : seq , delYn : "Y"},  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.Board.goList();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

iWORK.Board.bindFilePlus = function() {
	$("[data-itsp-fileAdd]").on("click", function() {
		var fileIndex = $('[id^=file_]').length+1; //현제 파일 개수
		var fileCnt = $('#fileCnt').val(); 	// 설정 파일개수
	
		if (fileIndex > fileCnt) {			
			alert("더 이상 추가할 수 없습니다.");
			return;
		}
		
		var html = '';
		html +=	'<li class="add_field" id="file'+ fileIndex +'">';
		html +=	'	<input type="file" name="file'+ fileIndex +'" id="file_'+ fileIndex +'" class="txt_field">';
		html +=	'	<input type="text" name="fileComment_'+fileIndex+'" id="fileComment_'+fileIndex+'" placeholder="첨부파일 설명글 또는 파일명" class="alt_txt">';
		html +=	'	<ul class="add_edit">';
		html +=	'		<li><a href="javascript:;" class="bt bt_inner" data-itsp-fileDel>삭제</a></li>';
		html +=	'	</ul>';
		html +=	'</li>';
		
		$('#fileList').append(html);		
	});
};

iWORK.Board.bindFileMinus = function() {
	$("#fileList").on("click", "[data-itsp-fileDel]", function(e) {
		var me = $(this);
		me.parents('li[class=add_field]').remove();
		e.preventDefault();
	});
};

iWORK.Board.bindFileReset = function() {
	$("#fileList").on("click", "[data-itsp-fileReplace]", function() {
		var agent = navigator.userAgent.toLowerCase();
		var me = $(this);
		var fileObj = $('#file_'+me.attr('data-itsp-fileReplace'));
		var fileObj2 = $('#fileComment_'+me.attr('data-itsp-fileReplace'));
		
		if (agent.indexOf("msie") != -1) {			
			fileObj.replaceWith(fileObj.clone(true) ); 
			fileObj2.replaceWith(fileObj2.clone(true) ); 
		} else { 
			fileObj.val(""); 
			fileObj2.val(""); 
		};
	});
};

iWORK.Board.bindCommentsSaveLink = function() {
	var form = $('#fmComments');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}
		
		itsp.ajax.showLoading();
		itsp.ajax.doPostFormJSON(form, function(data) {
			itsp.ajax.hideLoading();
			$('#fmComments input[name=seq]').val('');
			$('#fmComments textarea[name=contents]').val('');
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				itsp.ajax.doPostJSON('/Board/Comments/selectListData', {}, function(data) {
					commentListAjax();
				});
			} else {
				alert(data.header.message);
			}
		});
	};
	
	itsp.com.blockFormEnterKey(form, function() {
		// doSave();
	});
	
	$('[data-itsp-comments-save-link]').click(function(event) {
		doSave();
	});
};

$(document).on("click", "[data-itsp-comments-update-link]", function() {
	var me = $(this);
	var seq = me.attr('data-itsp-comments-update-link');
	$('#fmComments input[name=seq]').val(seq);
	$('#fmComments textarea[name=contents]').val($('#contents_'+seq).val());
	$('[data-itsp-comments-save-link]').trigger('click');
});

$(document).on("click", "[data-itsp-comments-delete]", function() {
	if (!confirm("삭제 하시겠습니까?")) {
		return;
	}
	var me = $(this);
	var seq = me.attr('data-itsp-comments-delete');
	
	itsp.ajax.doPostJSON('/Board/Comments/DeleteProcData', { seq : seq }, function(data) {
		if (data.header.code == itsp.ajax.CODE_SUCCESS) {
			commentListAjax();
		}else{
			alert(data.header.message);
		}
	});
});

$(document).on("click", "[data-itsp-comments-modify]", function() {
	var me = $(this);
	var idx = me.attr('data-itsp-comments-modify');
		
	if ($('#comments_box_'+idx).css("display") == "none"){
		$('#comments_box_'+idx).show();
		$('#comments_cont_'+idx).hide();
		$('#modify_'+idx).html("취소");
	}else{
		$('#comments_box_'+idx).hide();
		$('#comments_cont_'+idx).show();
		$('#modify_'+idx).html("수정");
	}
});

var commentListAjax = function() {
	itsp.ajax.doPostJSON('/Board/Comments/selectListData', {}, function(data) {
		var commentList = data.body;
		$('#commentCnt').html("["+commentList.length+"]");
		
		var commentListObj = $('#comment_list');
		commentListObj.empty();
		
		for (var inx=0; inx<commentList.length; inx++) {
			var commentInfo = commentList[inx];
			var html = '';
			html += '	<div class="comment_item">';
			html += '		<div class="write_info">';
			html += '			<strong class="write_name">'+ commentInfo.regName+'</strong>';
			html += '			<span class="write_date">'+ commentInfo.regDate+'</span>';
			html += '		</div>';
			if(null != commentInfo.regId && (sessionScopeMemberId == commentInfo.regId)){
				html += '			<ul class="write_edit">';
				html += '				<li><a href="javascript:;" class="on" data-itsp-comments-modify="' + commentInfo.seq + '"><span id="modify_' + commentInfo.seq + '">수정</span></a></li>';
				html += '				<li><a href="javascript:;" data-itsp-comments-delete="' + commentInfo.seq + '">삭제</a></li>';
				html += '			</ul>';
			}
			html += '		<div class="write_subject">' + commentInfo.contents + '</div>';
			html += '		<div class="message_write_comment" style="display: none;" id="comments_box_' + commentInfo.seq + '">';
			html += '			<div class="write_main">';
			html += '				<div class="write_text">';
			html += '					<textarea class="input_write_text" cols="30" rows="3" placeholder="댓글을 입력해주세요." id="contents_' + commentInfo.seq + '">' + commentInfo.contents + '</textarea>';
			html += '				</div>';
			html += '				<div class="bt_list">';
			html += '					<ul>';
			html += '						<li><label for="" class="blind">수정</label><button type="button" name="" id="" class="bt bt_black" data-itsp-comments-update="' + commentInfo.seq + '">수정</button></li>';
			html += '					</ul>';
			html += '				</div>';
			html += '			</div>';
			html += '		</div>';
			html += '	</div>';
			commentListObj.append(html);
		};
	});
};

iWORK.Board.bindReplyEditLink  = function() {
	$('[data-itsp-reply-edit-link]').click(function(event) {
		var me = $(this);
		var recordSeq = me.attr('data-itsp-reply-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/Board/ReplyEdit.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&recordSeq='+recordSeq;
	});
};

iWORK.Board.bindReplySaveLink = function() {
	var form = $('#fmReply');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}
		
		itsp.ajax.showLoading();
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
			}
		});
	};
	
	itsp.com.blockFormEnterKey(form, function() {
		// doSave();
	});
	
	$('[data-itsp-reply-save-link]').click(function(event) {
		doSave();
	});
};

iWORK.Board.bindReplyDeleteLink = function() {
	$('[data-itsp-reply-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-reply-delete-link');
		
		if (!confirm("삭제 하시겠습니까?\n삭제 후 복구가 불가능합니다.")) {
			return;
		}
		itsp.ajax.doPostJSON('/Board/Reply/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
			}
		});
	});
};


var appLoading = function() {
	iWORK.Board.bindCaptcha();
	iWORK.Board.bindNewSearchLink();
	iWORK.Board.bindListLink();
	iWORK.Board.bindPageLink();
	iWORK.Board.bindEditLink();
	iWORK.Board.bindViewLink();
	iWORK.Board.bindSaveLink();
	iWORK.Board.bindDeleteLink();
	
	iWORK.Board.bindFilePlus();
	iWORK.Board.bindFileMinus();
	iWORK.Board.bindFileReset();

	iWORK.Board.bindCommentsSaveLink();
	
	iWORK.Board.bindReplySaveLink();
	iWORK.Board.bindReplyDeleteLink();
};