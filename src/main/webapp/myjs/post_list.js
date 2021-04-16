var vm = new Vue({

    el:'#postdiv',
    data:{
        postlist:[],
        page:{},
        pageNum:1,
        pageSize:3,
        searchEntity:{},
        meunlist:[]
    },
    methods:{
        getPostListConn:function(){
            var _this = this;
            axios.post("../post/getPostListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function(response){
                _this.postlist = response.data.list;
                _this.pageNum = response.data.currentPage;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            });
        },
        paging:function(pageNum){
            this.pageNum = pageNum;
            this.getPostListConn();
        },
        toPostMeun:function(id){
            var _this = this;
            axios.get("../post/getMeunListById.do?id="+id).then(function(response){
                _this.meunlist = response.data
                testaa(response.data,id);
                document.getElementById("nodes").style.display="block";
            })
        },
        savePostMeun:function(postid,ids){
            var _this = this;
            axios.post("../post/savePostMeun.do?postid="+postid,ids).then(function(response){
                if(response.data.flag){
                    alert(response.data.msg);
                    document.getElementById("nodes").style.display="none";
                }else{
                    alert(response.data.msg);
                }
            })
        }

    }

});
vm.getPostListConn();