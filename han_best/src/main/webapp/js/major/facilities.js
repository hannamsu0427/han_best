var iWORK = iWORK ? iWORK : {};
iWORK.Major = iWORK.Major ? iWORK.Major : {};

iWORK.Major.goList = function() {
	var curSearchForm = $('#curSearchForm');
	var curPageForm = $('#curPageForm');
	location.href = '/iWORK/Major/FacilitiesList.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize();
};

iWORK.Major.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.Major.goList();
	});
};

iWORK.Major.bindNewSearchLink = function() {
	$('#searchBy').change(function(event){
		$('#curSearchForm input[name=searchBy]').attr('value', $('#searchBy').val());
	});
	
	$('[data-itsp-new-search-link]').click(function(event) {
		if (!itsp.formValidator.validate($('#newSearchForm'))) {
			return;
		}
		
		$('#curSearchForm input[name=searchValue]').attr('value', $('#searchValue').val());
		iWORK.Major.goList();
	});
};

iWORK.Major.bindPageLink = function() {
	$('[data-itsp-page-link]').click(function(event) {
		var me = $(this);
		var pageNum = me.attr('data-itsp-page-link');
		var curPage = me.attr('data-itsp-curPage');	
		$('#curPageForm input[name=pageNum]').attr('value', pageNum);
		$('#curPageForm input[name=curPage]').attr('value', curPage);
		iWORK.Major.goList();
	});
	
	$('#curPage').change(function(event){
		$('#curPageForm input[name=curPage]').attr('value', $('#curPage').val());
		iWORK.Major.goList();
	});
};

//등록 및 수정 화면
iWORK.Major.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var curSearchForm = $('#curSearchForm');
		var curPageForm = $('#curPageForm');
		location.href = '/iWORK/Major/Facilities.do?'+curSearchForm.serialize()+'&'+curPageForm.serialize()+'&seq='+seq;
	});
};


iWORK.Major.bindSaveLink = function() {
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
				iWORK.Major.goList();
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


iWORK.Major.bindDeleteLink = function() {
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
		
		if (!confirm("삭제 하시겠습니까?")) {
			return;
		}		
		
		itsp.ajax.doPostJSON('/Major/Facilities/deleteProcData', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

iWORK.Major.bindFilePlus = function() {
	$("[data-itsp-fileAdd]").on("click", function() {
		var fileIndex = $('[id^=file_]').length+1; //현제 파일 개수
		var html = '';
		html +=	'<li class="add_field" id="file'+ fileIndex +'">';
		html +=	'	<input type="file" name="file'+ fileIndex +'" id="file_'+ fileIndex +'" class="txt_field">';
		html +=	'	<input type="text" name="fileEtc_'+fileIndex+'" id="fileEtc_'+fileIndex+'" placeholder="첨부파일 설명글 또는 파일명" class="alt_txt">';
		html +=	'	<ul class="add_edit">';
		html +=	'		<li><a href="javascript:;" class="bt bt_inner" data-itsp-fileDel>삭제</a></li>';
		html +=	'	</ul>';
		html +=	'</li>';
		$('#fileList').append(html);
	});
};

iWORK.Major.bindFileMinus = function() {
	$("#Major").on("click", "[data-itsp-fileDel]", function(e) {
		var me = $(this);
		me.parents('li[class=add_field]').remove();
		e.preventDefault();
	});
};

iWORK.Major.orderNumChange = function(order) {
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

iWORK.Major.bindOrderNumLink = function() {
	$('[data-itsp-orderNum-link]').click(function(event) {
		itsp.ajax.showLoading();

		var dataArray = [];
		$("#changeOrder > li").each(function(event){
			var me = $(this);
			var seq = me.attr('data-itsp-seq');
			var orderNum = me.attr('data-itsp-orderNum');
			dataArray.push({ seq : seq, orderNum : orderNum});
		});
		itsp.ajax.doPostJSON('/iWORK/Major/Facilities/orderNumProc', { data : dataArray }, function(data) {
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
	iWORK.Major.bindNewSearchLink();
	iWORK.Major.bindListLink();
	iWORK.Major.bindPageLink();
	iWORK.Major.bindEditLink();
	iWORK.Major.bindSaveLink();
	iWORK.Major.bindDeleteLink();
	
	iWORK.Major.bindFilePlus();
	iWORK.Major.bindFileMinus();
	
	iWORK.Major.bindOrderNumLink();
	var order;
	$("#changeOrder").sortable({
		containment: "parent",
		update: function (event, ui) {
			order = $(this).sortable('toArray', {
				attribute: 'data-itsp-orderNum'
			});
			iWORK.Major.orderNumChange(order);
		}
	});
};