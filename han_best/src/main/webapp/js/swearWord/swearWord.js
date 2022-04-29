var iWORK = iWORK ? iWORK : {};
iWORK.swearWord = iWORK.swearWord ? iWORK.swearWord : {};

iWORK.swearWord.goList = function() {
	location.href = '/iWORK/SwearWord.do';
};

iWORK.swearWord.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.swearWord.goList();
	});
};

iWORK.swearWord.bindSaveLink = function() {
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
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.swearWord.goList();
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

var appLoading = function() {
	iWORK.swearWord.bindListLink();
	iWORK.swearWord.bindSaveLink();
};
