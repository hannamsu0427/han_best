var iWORK = iWORK ? iWORK : {};
iWORK.Board = iWORK.Board ? iWORK.Board : {};

iWORK.Board.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/Board/RecordList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
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
		location.href = '/iWORK/Board/Record.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.Board.bindConfigLink = function() {
	$('[data-itsp-config-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-config-link');
		location.href = '/iWORK/Board/Config.do?seq='+seq;
	});
};

iWORK.Board.bindCategoryLink = function() {
	$('[data-itsp-category-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-category-link');
		location.href = '/iWORK/Board/CategoryList.do?configSeq='+seq;
	});
};

iWORK.Board.bindRecordLink = function() {
	$('[data-itsp-record-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-record-link');
		location.href = '/iWORK/Board/RecordList.do?configSeq='+seq;
	});
};

iWORK.Board.bindTrashLink = function() {
	$('[data-itsp-trash-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-trash-link');
		location.href = '/iWORK/Board/Trash.do?configSeq='+seq;
	});
};

iWORK.Board.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
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
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

iWORK.Board.bindUseUpdateLink = function() {
	$('[data-itsp-use-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-use-link');
		var delYn = me.attr('data-itsp-delYn');
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
		
		if("Y" == delYn){
			if (!confirm("삭제 하시겠습니까?")) {
				return;
			}		
		}else{
			if (!confirm("복구 하시겠습니까?")) {
				return;
			}		
		}
		
		itsp.ajax.doPostJSON('/Board/Record/useProcData', { seq : seq , delYn : delYn},  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

var appLoading = function() {
	iWORK.Board.bindNewSearchLink();
	iWORK.Board.bindListLink();
	iWORK.Board.bindPageLink();
	iWORK.Board.bindEditLink();
	iWORK.Board.bindSaveLink();
	iWORK.Board.bindDeleteLink();
	
	iWORK.Board.bindConfigLink();
	iWORK.Board.bindCategoryLink();
	iWORK.Board.bindRecordLink();
	iWORK.Board.bindTrashLink();
};