$(document).ready(function(){
    $.ajax({
        type : "POST",
        // url : "http://52.79.242.145:8080/app/timeline/mtimeline",
        url : "http://39.127.7.58:8080/app/timeline/mtimeline",
        data : {pagenum : 1},
        success : function(result){
            console.log(result);
            var page="";
            var str="";
            $.each(result.mtimelineList,function(i, s){
               console.log(s);
               str +='<div class="timeline">';
               str +='<h3>'+s.name+'</h3>';
               str +='<h3 id="timeline_n">'+s.timeline_n+'</h3>';        
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
               
    }); // ajax함수


	 //글 등록
     $(document).on('click','#submit',function(){
         window.sessionStorage.getItem("id");
        
        var query = {
             content:$("#textarea").val(),
             user_id:("admin")
         };
        
         var log = JSON.stringify(query);
         alert(log);

         $.ajax({
             type : "POST",
            //  url : "http://52.79.242.145:8080/app/timeline/mtimeline.do",
             url : "http://39.127.7.58:8080/app/timeline/mtimeline.do",
             data : log,
             dataType : 'text',
             contentType : "application/json; charset=UTF-8",
             success : function(data){
                 if( data == "success"){
                     alert("등록 성공");
                     location.reload();

                     $.ajax({
                        type : "POST",
                       // url : "http://52.79.242.145:8080/app/send/message.do",
                        url : "http://39.127.7.58:8080/app/send/Timelinemessage.do",
                        success : function(data) {
                        }
                 }); //ajax 종료

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
            // var aa = $(this).next().text();//글내용
            var timeline_n = $(this).siblings().eq(1).text();//글번호
            var query = {
                timeline_n : timeline_n
            }
            var sig = JSON.stringify(query);
            alert(sig);
            $.ajax({
                async : true,
                type : "POST",
                // url : "http://52.79.242.145:8080/app/timeline/mtimelinedelete.do",
                url : "http://39.127.7.58:8080/app/timeline/mtimelinedelete.do",
                data : sig, 
                dataType : 'text',
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

    //글 수정버튼 클릭
    $(document).on('click','.modify',function(){
        alert("수정");
        var content = $(this).next().next().text();//글내용
        var timeline_n = $(this).siblings().eq(1).text();//글번호
    
        var query = {
            content : content,
            timeline_n : timeline_n
            
        }
        var sig = JSON.stringify(query);
        alert(sig);

        $.ajax({
            async : true,
            type : "POST",
            // url :  "http://52.79.242.145:8080/app/timeline/mtimelinemodify.do",
            url :  "http://39.127.7.58:8080/app/timeline/mtimelinemodify.do",
            data : sig, 
            dataType : 'json',
            contentType : "application/json; charset=UTF-8",
            success : function(data) {
               console.log(data);
               var str="";
               var temp="";
               var num="";
               
                num += '<textarea name="textarea1" id="textarea1"style="display:none;">'+data.timeline_n+'</textarea>'
                str += data.content;

                $("#submit").attr('id','modify'); 
                $("#textarea").html(str);
                $("#writing").html(temp);
                $(".pagination").html(temp);
                $("#writing").html(num);
                
            }
        }); // ajax 종료
            /*}*/

    });

    //글 수정
    $(document).on('click','#modify',function(){
    alert("수정");
    var content = $("#textarea").val();//글내용
     var timeline_n = $("#textarea1").val();//글번호
        alert(content);
    var query = {
        content : content,
        timeline_n : timeline_n
        
    }
    var sig = JSON.stringify(query);
    alert(sig);

    $.ajax({
        async : true,
        type : "POST",
        // url :  "http://52.79.242.145:8080/app/timeline/mtimelinemodifyy.do",
        url :  "http://39.127.7.58:8080/app/timeline/mtimelinemodifyy.do",
        data : sig, 
        dataType : 'text',
        contentType : "application/json; charset=UTF-8",
        success : function(data) {
            console.log(data);
            if (data == "success") {
                alert("수정되었습니다");
                location.reload();
            } else {
                alert("수정에 실패했습니다");
            }
        }
    }); // ajax 종료
        /*}*/

});
    
});

 


function page(index){
    alert(index);
    $.ajax({
        type : "POST",
        // url : "http://52.79.242.145:8080/app/timeline/mtimeline",
        url : "http://39.127.7.58:8080/app/timeline/mtimeline",
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