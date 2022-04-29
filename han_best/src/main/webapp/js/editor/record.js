var iWORK = iWORK ? iWORK : {};
iWORK.editor = iWORK.editor ? iWORK.editor : {};

iWORK.editor.bindSaveLink = function() {
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
				location.reload();
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

$(document).on("click", "[data-itsp-data-choice]", function() {
	var data = {};
	var seq = $('select[name=seq]').val();
	if("" == seq){
		alert("테이터를 선택해주세요.");
		$('select[name=seq]').focus();
		return;
	}else{
		data.configSeq = $("input[name=configSeq").val();
		data.seq = seq;
	}

	itsp.ajax.doPostJSON('/Editor/Record/JsonList', data, function(data) {
		var dataList = data.body;
		var dataObj = $('#contents');
		htmlDataAjax(dataList, dataObj, seq);
	});
});

var htmlDataAjax = function(dataList, dataObj, seq) {
	for (var inx=0; inx<dataList.length; inx++) {
		var dataInfo = dataList[inx];
		if(seq == dataInfo.seq){
			CKEDITOR.instances.contents.setData(dataInfo.contents);
		}
	};
};

var appLoading = function() {
	iWORK.editor.bindSaveLink();
};