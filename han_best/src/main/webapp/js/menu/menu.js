var iWORK = iWORK ? iWORK : {};
iWORK.menu = iWORK.menu ? iWORK.menu : {};

iWORK.menu.goList = function() {
	location.href = '/iWORK/Menu.do';
};

iWORK.menu.bindListLink = function() {
	$('[data-itsp-list-link]').click(function(event) {
		iWORK.menu.goList();
	});
};

//카테고리 추가 및 수정
iWORK.menu.bindEditLink = function() {
	$('[data-itsp-edit-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-edit-link');
		var parentSeq = me.attr('data-itsp-parentSeq');
		var depth = me.attr('data-itsp-depth');
		
		$('#curSearchForm input[name=seq]').attr('value', seq);
		$('#curSearchForm input[name=parentSeq]').attr('value', parentSeq);
		$('#curSearchForm input[name=depth]').attr('value', depth);
		
		var curSearchForm = $('#curSearchForm');
		curSearchForm.submit();
	});
};

iWORK.menu.bindSaveLink = function() {
	$('[data-itsp-save-link]').click(function(event) {
		var form = $('#fm');
		if (!itsp.formValidator.validate(form)) {
			return;
		}
		
		itsp.ajax.showLoading();
		if($("#contents").length) {
			itsp.com.getHtmlEditorData('contents');
		}
		
		if (!confirm("저장 하시겠습니까?")) {
			return;
		}
	
		itsp.ajax.doPostFormJSON(form, function(data) {
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				location.reload();
			} else {
				alert(data.header.message);
			}
		});
	});
};

iWORK.menu.bindDeleteLink = function() {
	$('[data-itsp-delete-link]').click(function(event) {
		var me = $(this);
		var seq = me.attr('data-itsp-delete-link');
		
		if (!confirm("삭제 된 데이터는 복구가 되지앖습니다.\n삭제 하시겠습니까?")) {
			return;
		}

		itsp.ajax.doPostJSON('/iWORK/Menu/deleteDataProc', { seq : seq },  function(data) {
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				iWORK.menu.goList();
			} else {
				alert(data.header.message);
				// 화면 이동 없음
			}
		});
	});
};

$(document).on("click", "[data-itsp-menu-sortOrder-link]", function() {
	itsp.ajax.showLoading();
	var dataArray = [];
	var inx = 001;
	$("#sdk > li").each(function(event){
		var me = $(this);
		var seq = me.attr('data-itsp-seq');
		me.attr('data-itsp-sortorder', pad(inx, 3));

		if(seq != undefined ){
			dataArray.push({ seq : parseInt(seq), depth: 1, parentSeq : 0, sortOrder : pad(inx, 3)});
		}

		var depth2 = me.children('ul');
		var inx2 = 001;
		depth2.children('li').each(function(event){
			var parentseq = $(this).attr('data-itsp-seq');
			$(this).attr('data-itsp-parentSeq', seq);
			$(this).attr('data-itsp-sortOrder', pad(inx, 3)+":"+pad(inx2, 3));
			dataArray.push({ seq : parseInt($(this).attr('data-itsp-seq')), depth: 2, parentSeq : parseInt(seq), sortOrder : pad(inx, 3)+":"+pad(inx2, 3) });

			var depth3 = $(this).children('ul');
			var inx3 = 001;
			depth3.children('li').each(function(event){
				$(this).attr('data-itsp-parentSeq', parentseq);
				$(this).attr('data-itsp-sortOrder', pad(inx, 3)+":"+pad(inx2, 3)+":"+pad(inx3, 3));
				dataArray.push({ seq : parseInt($(this).attr('data-itsp-seq')), depth: 3, parentSeq : parseInt(parentseq), sortOrder : pad(inx, 3)+":"+pad(inx2, 3)+":"+pad(inx3, 3) });
				inx3++;

				var depth4 = $(this).children('ul');
				if(depth4.children('li').length > 0 ){
					alert("메뉴 구조는 3단계 까지 가능합니다. \n메뉴 구조를 다시 재조정 바랍니다.");
					location.reload();
				}
			});
			inx2++;
		});
		inx++;
	});

	//console.log(dataArray);
	itsp.ajax.doPostJSON('/iWORK/Menu/sortOrderProc', { data : dataArray }, function(data) {
		itsp.ajax.hideLoading();
		if (data.header.code == itsp.ajax.CODE_SUCCESS) {
			alert(data.header.message);
			location.reload();
		} else {
			alert(data.header.message);
		}
	});

	function pad(n, width) {
		n = n + '';
		return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
	}

});

iWORK.menu.bindTypeLink = function() {
	typeChange(type);
	$(":input:radio[name=type]").click(function(){
		var me = $(this);
		typeChange(me.val());
	});
};

function typeChange(type) {
	$("[id^='function']").hide();
	$(":input[name=url]").prop('readonly', true);
	if("Link" == type){
		$(":input[name=url]").prop('readonly', false);
		$('#functionLink').show();
	}else if("Include" == type){
		$('#functionInclude').show();
	}else{
		$('#functionData').show();
		if("" != type){
			itsp.ajax.doPostJSON('/iWORK/'+type+'/Config/JsonList', {}, function(data) {
				var dataObj = $('#configSeq');
				dataObj.empty();
				var dataList = data.body;
				if(dataList.length > 0){
					for (var inx=0; inx<dataList.length; inx++) {
						var dataInfo = dataList[inx];
						var html = '';
						if(inx == 0){
							html += '	<option value="">선택</option>';
						}
						if("BoardList" == type){
							if(configSeq == dataInfo.seq ){
								html += '	<option value="'+dataInfo.seq+'" selected="selected">['+dataInfo.type+'] '+dataInfo.title+'</option>';
							}else{
								html += '	<option value="'+dataInfo.seq+'">['+dataInfo.type+'] '+dataInfo.title+'</option>';
							}
						}else{
							if(configSeq == dataInfo.seq ){
								html += '	<option value="'+dataInfo.seq+'" selected="selected">'+dataInfo.title+'</option>';
							}else{
								html += '	<option value="'+dataInfo.seq+'">'+dataInfo.title+'</option>';
							}
						}
						
						dataObj.append(html);
					};
				}
			});
		}	
	}
}

var appLoading = function() {
	iWORK.menu.bindListLink();
	iWORK.menu.bindEditLink();
	iWORK.menu.bindSaveLink();
	iWORK.menu.bindDeleteLink();
	iWORK.menu.bindTypeLink();
};