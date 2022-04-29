var iWORK = iWORK ? iWORK : {};
iWORK.popUp = iWORK.popUp ? iWORK.popUp : {};

iWORK.popUp.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/PopUpList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.popUp.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curSearchForm = $('#curSearchForm');
		location.href = '/iWORK/PopUpList.do?'+curSearchForm.serialize()+'&pageNum='+pageNum;
	});
	
	$('#curPage').change(function(event){
		$('input[name=curPage]').attr('value', $('#curPage').val());
		iWORK.popUp.goList();
	});
};

iWORK.popUp.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.popUp.goList();
	});
};

iWORK.popUp.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/iWORK/PopUp.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.popUp.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}

		itsp.ajax.showLoading();
		itsp.ajax.doPostFormJSON(form, function(data) {
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.popUp.goList();
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


iWORK.popUp.bindDeleteLink = function() {
	$('[data-itsp-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-delete-link');
		var isOk = true;

		if(seq == "chkBox"){
			seq = "";
			if($('input[name=chkBoxs]:checked').length ==0){
				alert("삭제할 데이터를 선택해 주세요.");
				isOk = false;
				return false;
			}

			$('input[name=chkBoxs]:checked').each(function(){
				seq += $(this).val() + ",";
			});
		}

		if(!isOk) return;

		if (!confirm("삭제 된 데이터는 복구가 되지앖습니다.\n삭제 하시겠습니까?")) {
			return;
		}

		itsp.ajax.doPostJSON('/iWORK/PopUp/deleteDataProc', { seq : seq },  function(data) {
				if (data.header.code == itsp.ajax.CODE_SUCCESS) {
					iWORK.popUp.goList();
				} else {
					alert(data.header.message);
					// 화면 이동 없음
				}
			}
		);
	});
};

iWORK.popUp.orderNumChange = function(order) {
	var dataArray = [];
	
	var min = order.reduce( function (previous, current) {
		return previous > current ? current:previous;
	});
	
	var index = min;
	$("#changeOrder > tr").each(function(event){
		var me = $(this);
		me.attr('data-itsp-orderNum', index);
		index++;
	});
};

iWORK.popUp.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > tr").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ seq : seq, order_num : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/PopUp/orderNumProc', { data : dataArray }, function(data) {
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
			}
		});
	});
};

var appLoading = function() {
	iWORK.popUp.bindPageLink();
	iWORK.popUp.bindListLink();
	iWORK.popUp.bindEditLink();
	iWORK.popUp.bindSaveLink();
	iWORK.popUp.bindDeleteLink();
	iWORK.popUp.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.popUp.orderNumChange(order);
		}
	});
};
