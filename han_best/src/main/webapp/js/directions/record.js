var iWORK = iWORK ? iWORK : {};
iWORK.Record = iWORK.Record ? iWORK.Record : {};

iWORK.Record.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/Directions/RecordList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.Record.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.Record.goList();
	});
};

iWORK.Record.bindNewSearchLink = function() {
	$('#searchBy').change(function(event){
		$('#curSearchForm input[name=searchBy]').attr('value', $('#searchBy').val());
	});
	
	$('[data-itsp-new-search-link]').click(function(event) {
		if (!itsp.formValidator.validate($('#newSearchForm'))) {
			return;
		}
		
		$('#curSearchForm input[name=searchValue]').attr('value', $('#searchValue').val());
		iWORK.Record.goList();
	});
};

iWORK.Record.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curPage = me.attr('data-itsp-curPage');	
		$('#curPageForm input[name=pageNum]').attr('value', pageNum);
		$('#curPageForm input[name=curPage]').attr('value', curPage);
		iWORK.Record.goList();
	});
	
	$('#curPage').change(function(event){
		$('#curPageForm input[name=curPage]').attr('value', $('#curPage').val());
		iWORK.Record.goList();
	});
};

//등록 및 수정 화면
iWORK.Record.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/iWORK/Directions/Record.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.Record.bindSaveLink = function() {
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
				iWORK.Record.goList();
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


iWORK.Record.bindDeleteLink = function() {
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
		
		itsp.ajax.doPostJSON('/Directions/Record/deleteProcData', { seq : seq },  function(data) {
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
	iWORK.Record.bindNewSearchLink();
	iWORK.Record.bindListLink();
	iWORK.Record.bindPageLink();
	iWORK.Record.bindEditLink();
	iWORK.Record.bindSaveLink();
	iWORK.Record.bindDeleteLink();
};