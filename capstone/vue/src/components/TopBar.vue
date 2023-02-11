<template>
  <header id="topbar">
    <div id="logo">
      <img src="/assets/vr-logo-transparent-navy.png" alt="Vinyl Revival logo" />
    </div>
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      >
      <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"
        >Login</router-link
      >&nbsp;|
      <router-link v-bind:to="{ name: 'collections' }">Collections</router-link
      >&nbsp;|
      <router-link v-bind:to="{ name: 'profile' }">Profile</router-link>
    </div>
    <router-view />
  </header>
</template>

<script>
export default {};
</script>

<style>
body {
  margin: 0;
}
#logo * {
  position: fixed;
  width: 300px;
  top: -150px;
}

#topbar {
  margin: 0;
  height: 50px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}
</style>