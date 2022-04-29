var iWORK = iWORK ? iWORK : {};
iWORK.History = iWORK.History ? iWORK.History : {};

iWORK.History.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/History/ConfigList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.History.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.History.goList();
	});
};

iWORK.History.bindNewSearchLink = function() {
	$('#searchBy').change(function(event){
		$('#curSearchForm input[name=searchBy]').attr('value', $('#searchBy').val());
	});
	
	$('[data-itsp-new-search-link]').click(function(event) {
		if (!itsp.formValidator.validate($('#newSearchForm'))) {
			return;
		}
		
		$('#curSearchForm input[name=searchValue]').attr('value', $('#searchValue').val());
		iWORK.History.goList();
	});
};

iWORK.History.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curPage = me.attr('data-itsp-curPage');	
		$('#curPageForm input[name=pageNum]').attr('value', pageNum);
		$('#curPageForm input[name=curPage]').attr('value', curPage);
		iWORK.History.goList();
	});
	
	$('#curPage').change(function(event){
		$('#curPageForm input[name=curPage]').attr('value', $('#curPage').val());
		iWORK.History.goList();
	});
};

//등록 및 수정 화면
iWORK.History.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/iWORK/History/Config.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.History.bindCategoryLink = function() {
	$('[data-itsp-category-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-category-link');
		location.href = '/iWORK/History/CategoryList.do?configSeq='+seq;
	});
};

iWORK.History.bindRecordLink = function() {
	$('[data-itsp-record-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-record-link');
		location.href = '/iWORK/History/RecordList.do?configSeq='+seq;
	});
};

iWORK.History.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.History.goList();
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


iWORK.History.bindDeleteLink = function() {
	$('[data-itsp-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-delete-link');
		var isOk = true;
		
		if(seq == "chkBox"){
			seq = "";
			if($('input[name=chkBoxs]:checked').length ==0){
				alert("삭제할 게시글을 선택해 주세요.");
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
		
		itsp.ajax.doPostJSON('/iWORK/History/Config/deleteProcData', { seq : seq },  function(data) {
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
	iWORK.History.bindNewSearchLink();
	iWORK.History.bindListLink();
	iWORK.History.bindPageLink();
	iWORK.History.bindEditLink();
	iWORK.History.bindCategoryLink();
	iWORK.History.bindRecordLink();
	iWORK.History.bindSaveLink();
	iWORK.History.bindDeleteLink();
};