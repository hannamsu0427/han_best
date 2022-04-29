var iWORK = iWORK ? iWORK : {};
iWORK.Schedule = iWORK.Schedule ? iWORK.Schedule : {};

iWORK.Schedule.goList = function() {
	location.href = '/iWORK/Schedule/RecordList.do';
};

iWORK.Schedule.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.Schedule.goList();
	});
};

//등록 및 수정 화면
iWORK.Schedule.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		location.href = '/iWORK/Schedule/Record.do?'+curSearchForm.serialize()+'&seq='+seq;
	});
};

iWORK.Schedule.bindConfigLink = function() {
	$('[data-itsp-config-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-config-link');
		location.href = '/iWORK/Schedule/Config.do?seq='+seq;
	});
};

iWORK.Schedule.bindRecordLink = function() {
	$('[data-itsp-record-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-record-link');
		location.href = '/iWORK/Schedule/RecordList.do?configSeq='+seq;
	});
};

iWORK.Schedule.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.Schedule.goList();
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


iWORK.Schedule.bindDeleteLink = function() {
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
		
		itsp.ajax.doPostJSON('/Schedule/Record/deleteProcData', { seq : seq },  function(data) {
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
	iWORK.Schedule.bindListLink();
	iWORK.Schedule.bindEditLink();
	iWORK.Schedule.bindSaveLink();
	iWORK.Schedule.bindDeleteLink();
	
	iWORK.Schedule.bindConfigLink();
	iWORK.Schedule.bindRecordLink();
};