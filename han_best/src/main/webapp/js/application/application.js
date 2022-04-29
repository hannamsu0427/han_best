var iWORK = iWORK ? iWORK : {};
iWORK.Application = iWORK.Application ? iWORK.Application : {};

iWORK.Application.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
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

iWORK.Application.bindDeleteLink = function() {
	$('[data-itsp-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-delete-link');
		var isOk = true;

		if (seq == "chkBox") {
			seq = "";
			if ($('input[name=chkBoxs]:checked').length == 0) {
				alert("삭제할 데이터를 선택해 주세요.");
				isOk = false;
				return false;
			}

			$('input[name=chkBoxs]:checked').each(function() {
				seq += $(this).val() + ",";
			});
		}

		if (!isOk)
			return;

		if (!confirm("삭제 하시겠습니까?")) {
			return;
		}

		itsp.ajax.doPostJSON('/iWORK/Application/deleteProcData', {
			seq : seq
		}, function(data) {
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
	iWORK.Application.bindSaveLink();
	iWORK.Application.bindDeleteLink();
};