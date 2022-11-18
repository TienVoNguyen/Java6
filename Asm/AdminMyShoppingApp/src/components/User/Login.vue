<template>
  <el-form :model="user" status-icon :rules="rules" ref="user" label-width="120px" class="demo-user">
    <el-form-item label="Username" prop="username">
      <el-input type="text" v-model="user.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input type="password" v-model="user.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('user')">Submit</el-button>
      <el-button @click="resetForm('user')">Reset</el-button>
    </el-form-item>
  </el-form>

</template>
<script>
import User from "@/models/user";
export default {
  name: "Login-User",
  data() {
    return {
      user: new User('', ''),
      rules: {
        password: [
          { required: true, message: 'Please input password name', trigger: 'blur' },
          { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' }
        ],
        username: [
          { required: true, message: 'Please input username', trigger: 'blur' },
          { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.user.username && this.user.password) {
            this.$store.dispatch('auth/login', this.user).then(
                () => {
                  this.$router.push('/');
                },
                error => {
                  this.message =
                      (error.response && error.response.data) ||
                      error.message ||
                      error.toString();
                }
            );
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }

  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/');
    }
  },
}
</script>