var vm = new Vue({
    el:'#userdiv',
    data:{
        userlist:[],
        entity:{},
        dlist:[],
        deptids:[],
        deptid:0
    },
    methods:{
        getUserList:function () {
            var _this = this;
            axios.get("../user/getUserLists.do").then(function (response) {
                _this.userlist = response.data;
            });
        },
        toUserDept:function(id){
            var _this = this;
            axios.get("../user/getUserById.do?id="+id).then(function(response){
                _this.entity =  response.data;
                _this.dlist = response.data.dlist;
                _this.deptids = response.data.deptids;
                document.getElementById("userDeptdiv").style.display="block";
            });
        },
        saveUserDapt:function(){
            var _this = this;
            axios.post("../user/saveUserDapt.do?id="+_this.entity.id,_this.deptids).then(function(response){
                if(response.data.flag){
                    document.getElementById("userDeptdiv").style.display="none";
                    _this.getUserList();
                }else{
                    alert(response.data.msg)
                }
            })
        },
        toUserPost:function(id){
            var _this = this;
            axios.get("../user/getUserInfo.do?id="+id).then(function(response){
                _this.entity = response.data
                _this.dlist = response.data.dlist
                document.getElementById("userPostdiv").style.display="block";
            })
        },
        saveUserPost:function(){
            this.entity.dlist = this.dlist;
            var _this = this;
            axios.post("../user/saveUserPost.do",_this.entity).then(function(response){
                if(response.data.flag){
                    alert(response.data.msg)
                    document.getElementById("userPostdiv").style.display="none"
                }else{
                    alert(response.data.msg)
                }
            })
        }

    }
});
vm.getUserList();