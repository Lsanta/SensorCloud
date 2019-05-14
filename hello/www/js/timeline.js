$(document).ready(function(){
    
	/*글 등록*/
	$("#submit").click(function(){
  
        var query = {
            content:$("#textarea").val(),
            user_id:("admin")
        };
        
        var log = JSON.stringify(query);
        alert(log);

        $.ajax({
            type : "POST",
            url : "http://39.127.7.58:8080/app/mtimeline.do",
            data : log,
            contentType : "application/json; charset=UTF-8",
            success : function(data){
                if( data == "success"){
                    alert("등록 성공");
                    location.reload();
                } else{
                    alert("ㄴㄴ");
                    location.reload();
                }
            }
        });
    }); 

    //글 삭제
    $(document).on('click','.delete',function(){
        if (confirm("정말 삭제하시겠습니까?") == true) {
        } else {
            return;
        }

        if (true) {
            var content = $(this).parent();
            var content2 = content.eq(0);
            var content3 = content2.text();
            var d = document.querySelecter('timeline').innerHtml;
         
            var query = {
                content : content3
            }
            var log = JSON.stringify(query);
            alert(log);
            alert(d);

            $.ajax({
                type : "POST",
                url : "http://39.127.7.58:8080/app/mtimelinedelte.do",
                data : log,
                contentType : "application/json; charset=UTF-8",
                success : function(data) {
                    if (data == "success") {
                        alert("삭제되었습니다");
                        location.reload();
                    } else {
                        alert("삭제에 실패했습니다");
                    }
                }
            }); // ajax 종료
                /*}*/
        }
    });
    //글 수정
    $(".modify").on('click',function() {
        alert("수정");
		var div = $(this).parent().attr('id');

		var content = $(this).parent();
		var content2 = content.siblings().first();
		var content3 = content2.text();

		var query = {
				content : content3
		}

		window.location.href = "timeline/timelinemodify?content="+ content3+"/"+div;

	});

});



function page(index){
    alert(index);
    $.ajax({
        type : "POST",
        url : "http://39.127.7.58:8080/app/mtimeline",
        data : {pagenum : index},
        success : function(result){
            console.log(result);
            var page="";
            var str="";
            $.each(result.mtimelineList,function(i, s){
               console.log(s);
               str +='<div class=\"timeline\">';
               str +='<h3 id="name">'+s.name+'</h3>';        
               str +='<h5>'+s.time+'</h5>';
               str +='<button class="modify">수정</button>';
               str +='<button class="delete">삭제</button>';
               str +='<p>'+s.content+'</p>';
               str +='</div>';
        
                $("#writing").html(str);
            });
               
            if (result.criteria.prev) {
                page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.startPage - 1) + ');" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>'
            }
            for (var i = result.criteria.startPage; i <= result.criteria.endPage; i++) {
                page += '<li class="page-item"><a class="page-link" href="javascript:page(' + i + ');">' + i + '</a></li>'
            }
            if (result.criteria.next) {
                page += '<li class="page-item"><a class="page-link" href="javascript:page(' + (result.criteria.endPage + 1) + ');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>'
            }
            $(".pagination").empty();
            $(".pagination").prepend(page);
            $("html, body").animate({ scrollTop: 0 }, 1);

        } // success 함수 종료
    });
};