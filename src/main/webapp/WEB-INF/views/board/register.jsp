<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="../includes/header.jsp"%>
<link href="/static/css/uploadAjax.css" rel="stylesheet">
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시판 글쓰기</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                게시판 글쓰기
            </div>
            <div class="panel-body">
                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>제목</label><input class="form-control" name="title" />
                    </div>
                    <div class="form-group">
                        <label>내용</label><textarea class="form-control" row="3" name="content"></textarea>
                    </div>
                    <div class="form-group">
                        <label>작성자</label><input class="form-control" name="writer">
                    </div>
                    <button type="submit" class="btn btn-default">등록</button>
                    <button type="rest" class="btn btn-default">취소</button>
                </form>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                파일 첨부
            </div>
            <div class="panel-body">
                    <div class="form-group uploadDiv">
                        <input type="file" name="uploadFile" multiple />
                    </div>
                    <div class="form-group uploadResult">
                        <ul>

                        </ul>
                    </div>
                </form>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="bigPictureWrapper">
    <div class="bigPicture">

    </div>
</div>

<script>
    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 5242880; // 5MB

    function checkExtension(fileName, fileSize){
        if(fileSize >= maxSize){
            alert("파일 사이즈 초과");
            return false;
        }

        if(regex.test(fileName)){
            alert("해당 종류의 파일은 업로드할 수 없습니다.");
            return false;
        }

        return true;
    }

    function showImage(fileCallPath){
        $(".bigPictureWrapper").css("display", "flex").show();
        $(".bigPicture")
            .html("<img src='/display?fileName="+encodeURIComponent(fileCallPath)+"'>")
            .animate({width:'100%', height:'100%'}, 1000);
    }

    function showUploadedFile(uploadResultArr){
        if(!uploadResultArr || uploadResultArr.length == 0) { return;}

        var uploadUL = $(".uploadResult ul");

        var str="";

        $(uploadResultArr).each(function(i, obj){
            if(obj.image) {
                var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_"+obj.uuid+"_"+obj.fileName);
                var originPath = obj.uploadPath+"\\"+obj.uuid+"_"+obj.fileName;
                originPath = originPath.replace(new RegExp(/\\/g), "/");

                console.log(fileCallPath);
                str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'><div>" +
                    "<span>"+obj.fileName+"</span>" +
                    "<button type='button' data-file=\'"+fileCallPath +"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br/>" +
                    "<a href=\"javascript:showImage(\'"+originPath+"\')\">" +
                    "<img src='/display?fileName="+fileCallPath+"'></a>" +
                "</div></li>";
            } else {
                var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
                var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
                str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'><div>" +
                    "<span>"+obj.fileName+"</span>" +
                    "<button type='button' data-file=\'"+fileCallPath +"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br/>" +
                    "<a href='/download?fileName="+fileCallPath+"'>" +
                    "<img src='/static/img/attach.png'></a>" +
                    "</div></li>";
            }
        });
        uploadUL.append(str);
    }

    $(document).ready(function(e){
       var formObj = $("form[role='form']");
       var cloneObj = $(".uploadDiv").clone();

       $("button[type='submit']").on('click', function(e){
           e.preventDefault();
           console.log("submit clicked");
       });

       $("input[type='file']").change(function(e){
           var formData = new FormData();
           var inputFile = $("input[name='uploadFile']");
           var files = inputFile[0].files;

           for(var i=0; i<files.length; i++){
               if(!checkExtension(files[i].name, files[i].size)){
                   return false;
               }
               formData.append("uploadFile", files[i]);
           }

           $.ajax({
               url : '/uploadAjaxAction',
               processData : false,
               contentType : false,
               data : formData,
               type : 'POST',
               dataType:'json',
               success : function(result){
                   console.log(result);
                   showUploadedFile(result);
                   $(".uploadDiv").html(cloneObj.html());
               }
           });
       });

        $(".bigPictureWrapper").on('click', function(e){
            $(".bigPicture").animate({width:'0%', height:'0%'}, 1000);
            setTimeout(() => {
                    $(this).hide();
            }, 1000);
        });

        $(".uploadResult").on("click", "button", function(e){
           console.log("delete file");

            var targetFile = $(this).data("file");
            var type = $(this).data("type");
            var targetLi = $(this).closest("li");
            console.log(targetFile);

            $.ajax({
                url:'/deleteFile',
                data:{fileName: targetFile, type:type},
                dataType:'text',
                type:'POST',
                success:function(result){
                    alert(result);
                    targetLi.remove();
                }
            });
        });

    });
</script>
<%@include file="../includes/footer.jsp"%>