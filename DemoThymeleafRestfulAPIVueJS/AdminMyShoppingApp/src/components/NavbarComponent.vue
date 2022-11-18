<template>
  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
    <el-menu-item index="1">
      <router-link to="/" class="nav-link">
        <i class="el-icon-s-home"></i>Home
      </router-link>
    </el-menu-item>
    <el-menu-item index="2">
      <router-link to="/order" class="nav-link">
        <i class="el-icon-notebook-2"></i> OrderManagement
      </router-link>
    </el-menu-item>
    <el-menu-item index="3">
      <router-link to="/product" class="nav-link">
        <i class="el-icon-circle-plus"></i>Product Management
      </router-link>
    </el-menu-item>
    <template v-if="!currentUser">
      <el-menu-item index="4" >
        <router-link  to="/login" class="nav-link">
          <i class="el-icon-circle-plus"></i>Signin
        </router-link>
      </el-menu-item>
      <el-menu-item index="5">
        <router-link to="/register" class="nav-link">
          <i class="el-icon-circle-plus"></i>Signup
        </router-link>
      </el-menu-item>
    </template>
    <template v-if="currentUser">
      <el-menu-item index="4">
        <el-link type="danger" href @click.prevent="logOut" class="nav-link">
          <b-icon icon="arrow-bar-right"></b-icon>
          Logout
        </el-link>
      </el-menu-item>
    </template>
  </el-menu>
</template>
<script>
export default {
  name: "NavbarComponent",
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
}
</script>
