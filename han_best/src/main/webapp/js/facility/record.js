var iWORK = iWORK ? iWORK : {};
iWORK.Facility = iWORK.Facility ? iWORK.Facility : {};

iWORK.Facility.goList = function() {
	var curSearchForm = $('#curSearchForm');
	location.href = '/iWORK/Facility/RecordList.do?'+curSearchForm.serialize();
};

iWORK.Facility.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.Facility.goList();
	});
};

iWORK.Facility.bindNewSearchLink = function() {
	$('#searchBy').change(function(event){
		$('#curSearchForm input[name=searchBy]').attr('value', $('#searchBy').val());
	});
	
	$('[data-itsp-new-search-link]').click(function(event) {
		if (!itsp.formValidator.validate($('#newSearchForm'))) {
			return;
		}
		
		$('#curSearchForm input[name=searchValue]').attr('value', $('#searchValue').val());
		iWORK.Facility.goList();
	});
};


//등록 및 수정 화면
iWORK.Facility.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		location.href = '/iWORK/Facility/Record.do?'+curSearchForm.serialize()+'&seq='+seq;
	});
};

iWORK.Facility.bindConfigLink = function() {
	$('[data-itsp-config-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-config-link');
		location.href = '/iWORK/Facility/Config.do?seq='+seq;
	});
};

iWORK.Facility.bindRecordLink = function() {
	$('[data-itsp-record-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-record-link');
		location.href = '/iWORK/Facility/RecordList.do?configSeq='+seq;
	});
};

iWORK.Facility.bindSaveLink = function() {
	var form = $('#fm');
	var doSave = function() {
		if (!itsp.formValidator.validate(form)) {
			return;
		}
		
		var etc1 = "";
		$("input[name=etc_1]").each(function(){
			if($(this).val()==""){
				etc1 += " "+"||";
			}else{
				etc1 += $(this).val() + "||";
			}
		});
		$('#fm input[name=etc1]').attr('value', etc1);
		
		var etc2 = "";
		$("input[name=etc_2]").each(function(){
			if($(this).val()==""){
				etc2 += " "+"||";
			}else{
				etc2 += $(this).val() + "||";
			}
		});
		$('#fm input[name=etc2]').attr('value', etc2);
		
		itsp.ajax.doPostFormJSON(form, function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.Facility.goList();
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

iWORK.Facility.bindDeleteLink = function() {
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
		
		itsp.ajax.doPostJSON('/Facility/Record/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

iWORK.Facility.bindFileEvent = function() {
	$("[data-itsp-fileAdd]").on("click", function() {
		var fileIndex = $('[id^=file_]').length+1; //현제 파일 개수
		var html = '';
		html +=	'<li class="add_field" id="file'+ fileIndex +'">';
		html +=	'	<input type="file" name="file'+ fileIndex +'" id="file_'+ fileIndex +'" class="p35">';
		html +=	'	<input type="text" name="fileEtc_'+fileIndex+'" id="fileEtc_'+fileIndex+'" placeholder="설명글" class="p40">';
		html +=	'	<ul class="add_edit">';
		html +=	'		<li><a href="javascript:;" class="bt bt_inner" data-itsp-fileRemove>삭제</a></li>';
		html +=	'	</ul>';
		html +=	'</li>';
		$('#fileList').append(html);		
	});
	
	$("#fileList").on("click", "[data-itsp-fileRemove]", function(e) {
		var me = $(this);
		me.parents('li[class=add_field]').remove();
		e.preventDefault();
	});
	
};

iWORK.Facility.bindEtcEvent = function() {
	$("[data-itsp-EtcAdd]").on("click", function() {
		var len = $('[id^=etc_]').length+1; //현제 파일 개수
		var html = '';
		html +=	'<li class="add_field" id="etc_'+ len +'">';
		html +=	'	<input type="text" name="etc_1" value="" title="명칭" placeholder="명칭" class="p40">';
		html +=	'	<input type="text" name="etc_2" value="" title="설명" placeholder="설명" class="p40">';
		html +=	'	<ul class="add_edit">';
		html +=	'		<li><a href="#" class="bt_inner" data-itsp-etcRemove>삭제</a></li>';
		html +=	'	</ul>';
		html +=	'</li>';
		$('#etcList').append(html);		
	});
	
	$("#etcList").on("click", "[data-itsp-etcRemove]", function(e) {
		var me = $(this);
		me.parents('li[class=add_field]').remove();
		e.preventDefault();
	});
};

iWORK.Facility.orderNumChange = function(order) {
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

iWORK.Facility.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > tr").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ seq : seq, orderNum : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/Facility/Record/orderNumProc', { data : dataArray }, function(data) {
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
	iWORK.Facility.bindNewSearchLink();
	iWORK.Facility.bindListLink();
	iWORK.Facility.bindEditLink();
	iWORK.Facility.bindSaveLink();
	iWORK.Facility.bindDeleteLink();
	
	iWORK.Facility.bindConfigLink();
	iWORK.Facility.bindRecordLink();
	
	iWORK.Facility.bindFileEvent();
	iWORK.Facility.bindEtcEvent();
	
	iWORK.Facility.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.Facility.orderNumChange(order);
		}
	});
};