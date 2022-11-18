<template>
  <el-container>
    <el-main>
      <el-form label-width="100px" :model="user" ref="user">
        <el-form-item label="UserName">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="Full name">
          <el-input v-model="user.fullName"></el-input>
        </el-form-item>
        <el-form-item label="Email">
          <el-input type="email" v-model="user.email"></el-input>
        </el-form-item>
        <el-form-item label="Password">
          <el-input type="password" v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">Submit</el-button>
          <!--        <el-button @click="resetForm('user')">Reset</el-button>-->
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer v-if="message"
               class="alert"
               :class="successful ? 'alert-success' : 'alert-danger'">
      {{ message }}
    </el-footer>
  </el-container>
</template>

<script>
import User from "@/models/user";

export default {
  name: "RegisterUser",
  data() {
    return {
      user: new User('', '', '', ''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/login');
    }
  },
  methods: {
    handleRegister() {
      console.log("start register")
      this.message = '';
      this.submitted = true;
      console.log(this.user)
      this.$store.dispatch('auth/register', this.user).then(
          data => {
            this.message = data.message;
            this.successful = true;
          },
          error => {
            this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            this.successful = false;
          }
      );

    }
  }
}
</script>

<style scoped>

</style>