var iWORK = iWORK ? iWORK : {};
iWORK.member = iWORK.member ? iWORK.member : {};

iWORK.member.bindModalLink = function() {
	$('[data-itsp-modal-open]').click(function(event) {
		itsp.ajax.showLoading();
		iWORK.member.goList();
		$('.modal_popup').addClass('on');
		itsp.ajax.hideLoading();
	});
	
	$('.bt_search').click(function(event) {
		var searchName = $("input[name='searchName']").val();
		if("" == searchName){
			alert("검색어는 필수입니다.");
			$("input[name='searchName']").focus();
			return false;
		}
		itsp.ajax.showLoading();
		itsp.ajax.doPostJSON('/iWORK/hjMember/JsonList', { searchBy : $("select[name='searchBy']").val(), searchName : searchName }, function(data) {
			var dataList = data.body;
			$("#hjAjaxDataList").empty();
			for (var inx = 0; inx < dataList.length; inx++) {
				var data = dataList[inx];
				var html = itsp.com.dynamicStringFromTemplateText('hjDataTemplate', data).trim();
				$("#hjAjaxDataList").append(html);
			}
			itsp.ajax.hideLoading();
		});
	});
	
	$('.modal_popup_wrap .bt_close').click(function(event) {
		$('.modal_popup').removeClass('on');
	});
	
	$('[data-itsp-member-save-link]').click(function(event) {
		if($('input[name=chkBoxs]:checked').length ==0){
			alert("관리자 부여 대상을 선택해 주세요.");
			return false;
		}
		
		itsp.ajax.showLoading();
		var dataArray = [];
		$('input[name=chkBoxs]:checked').each(function(){
			var str = $(this).val();
			var strArr = str.split('_');
			var isOk = true;
			$('[data-itsp-member-del-link]').each(function(){
				var me = $(this);
				var user_id = me.attr('data-itsp-member-del-link');
				if(user_id == strArr[0]){
					isOk = false;
					return false;
				}
			});
			if(isOk){
				dataArray.push({ user_id : strArr[0], user_nm: strArr[1]});
			}
		});
		
		console.log(dataArray);
		itsp.ajax.doPostJSON('/iWORK/Member/dataSaveProc', { data : dataArray }, function(data) {
			itsp.ajax.hideLoading();
			if (data.header.code == itsp.ajax.CODE_SUCCESS) {
				$('input[name=chkBoxs]').prop('checked', false);
				iWORK.member.goList();
			} else {
				alert(data.header.message);
			}
		});
	});
};

$(document).on("click", "[data-itsp-member-del-link]", function() {
	var me = $(this);
	var user_id = me.attr('data-itsp-member-del-link');

	if (!confirm("삭제 된 데이터는 복구가 되지앖습니다.\n삭제 하시겠습니까?")) {
		return;
	}

	itsp.ajax.doPostJSON('/iWORK/Member/deleteDataProc', { data : user_id },  function(data) {
		if (data.header.code == itsp.ajax.CODE_SUCCESS) {
			iWORK.member.goList();
		} else {
			alert(data.header.message);
			// 화면 이동 없음
		}
	});
});

iWORK.member.goList = function() {
	itsp.ajax.doPostJSON('/iWORK/Member/JsonList', { }, function(data) {
		var dataList = data.body;
		$("#MemberAjaxDataList").empty();
		$("#memberList").empty();
		for (var inx = 0; inx < dataList.length; inx++) {
			var data = dataList[inx];
			var html = itsp.com.dynamicStringFromTemplateText('memberDataTemplate', data).trim();
			$("#MemberAjaxDataList").append(html);
			
			var html2 = itsp.com.dynamicStringFromTemplateText('dataTemplate', data).trim();
			$("#memberList").append(html2);
		}
	});
};

var appLoading = function() {
	iWORK.member.goList();
	iWORK.member.bindModalLink();
};