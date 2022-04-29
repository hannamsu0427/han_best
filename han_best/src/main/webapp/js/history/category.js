var iWORK = iWORK ? iWORK : {};
iWORK.History = iWORK.History ? iWORK.History : {};

//등록 및 수정 화면
iWORK.History.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		location.href = '/iWORK/History/Category.do?categorySeq='+seq;
	});
};

iWORK.History.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.href = '/iWORK/History/CategoryList.do?configSeq='+$('#fm input[name=configSeq]').val();
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
		
		itsp.ajax.doPostJSON('/iWORK/History/Category/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
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

iWORK.History.orderNumChange = function(order) {
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

iWORK.History.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > tr").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ categorySeq : seq, orderNum : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/History/Category/orderNumProc', { data : dataArray }, function(data) {
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
	iWORK.History.bindEditLink();

	iWORK.History.bindSaveLink();
	iWORK.History.bindDeleteLink();
	
	iWORK.History.bindConfigLink();
	iWORK.History.bindCategoryLink();
	iWORK.History.bindRecordLink();
	
	iWORK.History.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.History.orderNumChange(order);
		}
	});
	
};