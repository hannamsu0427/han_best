var iWORK = iWORK ? iWORK : {};
iWORK.Board = iWORK.Board ? iWORK.Board : {};

//등록 및 수정 화면
iWORK.Board.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		location.href = '/iWORK/Board/Category.do?categorySeq='+seq;
	});
};

iWORK.Board.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.href = '/iWORK/Board/CategoryList.do?configSeq='+$('#fm input[name=configSeq]').val();
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


iWORK.Board.bindDeleteLink = function() {
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
		
		itsp.ajax.doPostJSON('/iWORK/Board/Category/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

iWORK.Board.bindConfigLink = function() {
	$('[data-itsp-config-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-config-link');
		location.href = '/iWORK/Board/Config.do?seq='+seq;
	});
};

iWORK.Board.bindCategoryLink = function() {
	$('[data-itsp-category-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-category-link');
		location.href = '/iWORK/Board/CategoryList.do?configSeq='+seq;
	});
};

iWORK.Board.bindRecordLink = function() {
	$('[data-itsp-record-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-record-link');
		location.href = '/iWORK/Board/RecordList.do?configSeq='+seq;
	});
};

iWORK.Board.bindTrashLink = function() {
	$('[data-itsp-trash-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-trash-link');
		location.href = '/iWORK/Board/Trash.do?configSeq='+seq;
	});
};

iWORK.Board.orderNumChange = function(order) {
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

iWORK.Board.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > tr").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ categorySeq : seq, orderNum : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/Board/Category/orderNumProc', { data : dataArray }, function(data) {
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
	iWORK.Board.bindEditLink();

	iWORK.Board.bindSaveLink();
	iWORK.Board.bindDeleteLink();
	
	iWORK.Board.bindConfigLink();
	iWORK.Board.bindCategoryLink();
	iWORK.Board.bindRecordLink();
	iWORK.Board.bindTrashLink();
	
	iWORK.Board.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.Board.orderNumChange(order);
		}
	});
	
};