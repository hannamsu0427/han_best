var iWORK = iWORK ? iWORK : {};
iWORK.googleanalytics = iWORK.googleanalytics ? iWORK.googleanalytics : {};

iWORK.googleanalytics.goList = function() {
	location.href = '/manager/GoogleAnalytics.do';
};

iWORK.googleanalytics.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.googleanalytics.goList();
	});
};

iWORK.googleanalytics.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}

		itsp.ajax.showLoading();
		if($("#content").length) {
			itsp.com.getHtmlEditorData('content');
		}
		itsp.ajax.doPostFormJSON(form, function(data) {
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.googleanalytics.goList();
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
	iWORK.googleanalytics.bindListLink();
	iWORK.googleanalytics.bindSaveLink();
};
