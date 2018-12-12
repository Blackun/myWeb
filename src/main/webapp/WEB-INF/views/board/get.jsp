<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../includes/header.jsp"%>
<link href="/static/css/board.css" rel="stylesheet">
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시물 보기</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Board Read Page
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label><input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
                </div>
                <div class="form-group">
                    <label>제목</label> <input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
                </div>
                <div class="form-group">
                    <label>내용</label><textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
                </div>

                <div class="form-group">
                    <label>작성자</label><input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
                </div>
                <button data-oper='modify' class="btn btn-default">수정</button>
                <button data-oper='list' class="btn btn-info">목록</button>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

    <div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-heading">파일</div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class='uploadResult'>
                    <ul>
                    </ul>
                </div>
            </div>
            <!--  end panel-body -->
        </div>
        <!--  end panel-body -->
    </div>
    <!-- end panel -->
</div>
<!-- /.row -->

<div class='bigPictureWrapper'>
    <div class='bigPicture'>
    </div>
</div>
<script type="text/javascript">
    function showImage(fileCallPath){
        $(".bigPictureWrapper").css("display","flex").show();
        $(".bigPicture")
            .html("<img src='/display?fileName="+fileCallPath+"' >")
            .animate({width:'100%', height: '100%'}, 1000);
    }

    $(document).ready(function() {
        var bno = '<c:out value="${board.bno}"/>';
        var operForm = $("#operForm");

        $("button[data-oper='modify']").on("click", function(e){
            alert("수정");
        });


        $("button[data-oper='list']").on("click", function(e){
            location.href="/board/list";
        });

        $.getJSON("/board/getAttachList", {bno: bno}, function(arr) {
            console.log(arr);
            var str = "";
            $(arr).each(function (i, attach) {
                //image type
                if (attach.fileType) {
                    var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
                    str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
                    str += "<img src='/display?fileName=" + fileCallPath + "'>";
                    str += "</div>";
                    str + "</li>";
                } else {
                    str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "' ><div>";
                    str += "<span> " + attach.fileName + "</span><br/>";
                    str += "<img src='/static/img/attach.png'></a>";
                    str += "</div>";
                    str + "</li>";
                }
            });

            $(".uploadResult ul").html(str);
        });

        $(".uploadResult").on("click","li", function(e){
            console.log("view image");
            var liObj = $(this);
            var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
            if(liObj.data("type")){
                showImage(path.replace(new RegExp(/\\/g),"/"));
            }else {
                //download
                self.location ="/download?fileName="+path
            }
        });

        $(".bigPictureWrapper").on("click", function(e){
            $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
            setTimeout(function(){
                $('.bigPictureWrapper').hide();
            }, 1000);
        });
    });
</script>
<%@include file="../includes/footer.jsp"%>