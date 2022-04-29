var iWORK = iWORK ? iWORK : {};
iWORK.History = iWORK.History ? iWORK.History : {};

iWORK.History.goList = function() {
	location.href = '/iWORK/History/RecordList.do';
};

iWORK.History.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.History.goList();
	});
};

//등록 및 수정 화면
iWORK.History.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		location.href = '/iWORK/History/Record.do?'+curSearchForm.serialize()+'&seq='+seq;
	});
};

iWORK.History.bindConfigLink = function() {
	$('[data-itsp-config-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-config-link');
		location.href = '/iWORK/History/Config.do?seq='+seq;
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
		
		itsp.ajax.doPostJSON('/History/Record/deleteProcData', { seq : seq },  function(data) {
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
	iWORK.History.bindListLink();
	iWORK.History.bindEditLink();
	iWORK.History.bindSaveLink();
	iWORK.History.bindDeleteLink();
	
	iWORK.History.bindConfigLink();
	iWORK.History.bindCategoryLink();
	iWORK.History.bindRecordLink();
};