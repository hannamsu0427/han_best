var iWORK = iWORK ? iWORK : {};
iWORK.visual = iWORK.visual ? iWORK.visual : {};

iWORK.visual.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/VisualList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.visual.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curSearchForm = $('#curSearchForm');
		location.href = '/iWORK/VisualList.do?'+curSearchForm.serialize()+'&pageNum='+pageNum;
	});
};

iWORK.visual.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.visual.goList();
	});
};

iWORK.visual.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/iWORK/Visual.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};

iWORK.visual.bindSaveLink = function() {
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
				iWORK.visual.goList();
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


iWORK.visual.bindDeleteLink = function() {
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

		itsp.ajax.doPostJSON('/iWORK/Visual/deleteDataProc', { seq : seq },  function(data) {
				if (data.header.code == itsp.ajax.CODE_SUCCESS) {
					iWORK.visual.goList();
				} else {
					alert(data.header.message);
					// 화면 이동 없음
				}
			}
		);
	});
};

iWORK.visual.orderNumChange = function(order) {
	var dataArray = [];
	
	var min = order.reduce( function (previous, current) {
		return previous > current ? current:previous;
	});
	
	var index = min;
	$("#changeOrder > li").each(function(event){
		var me = $(this);
		me.attr('data-itsp-orderNum', index);
		index++;
	});
};

iWORK.visual.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > li").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ seq : seq, orderNum : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/Visual/orderNumProc', { data : dataArray }, function(data) {
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
	iWORK.visual.bindPageLink();
	iWORK.visual.bindListLink();
	iWORK.visual.bindEditLink();
	iWORK.visual.bindSaveLink();
	iWORK.visual.bindDeleteLink();
	
	iWORK.visual.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.visual.orderNumChange(order);
		}
	});
};
