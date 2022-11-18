<template>
  <el-skeleton :rows="6" animated />
</template>

<script>
export default {
  name: "AutoLogin",
  created() {
      const param = this.$route.params.param;

      this.$store.dispatch('auth/autologin', param).then(
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
}
</script>

<style scoped>

</style>