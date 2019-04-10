$(document).ready(function(){

    $(".all").on("click",function(){
        $(".nav").addClass("on");
        $(".dim").show();
    });
    $(".nav .close").on("click",function(){
        $(".nav").removeClass("on");
        $(".dim").hide();
    });
    $(".man").on("click",function(){
        $(".a2").addClass("open");
        $(".dim").show();
    });
    $(".manclose").on("click",function(){
        $(".a2").removeClass("open");
        $(".dim").hide();
    });
                  
    $(".dim").on("click",function(){
        $(".nav").removeClass("on");
        $(".dim").hide();
    });
     $(".dim").on("click",function(){
        $(".a2").removeClass("open");
        $(".dim").hide();
    });
  
});