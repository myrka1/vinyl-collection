<template>
  <div id="app">
    <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
    <div id="nav">
      <div id="logo">
        <img id="icon" src='../src/assets/VR-Icon-Logo.png' />
        <img id="title" src='../src/assets/VR-Title-Logo.png' />
        <img id="slogan" src='../src/assets/VR-Slogan-Logo.png' />
      </div>
      <!--<img src="../assets/vr-logo.png" alt="Vinyl Revival logo"> -->
      <div id="router-stuff">
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"><strong>Logout</strong></router-link>
        <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''"><strong>Login</strong></router-link>&nbsp;
        <router-link v-bind:to="{ name: 'collections' }"><strong>Collections</strong></router-link>&nbsp;
        <router-link v-bind:to="{ name: 'profile' }" v-if="$store.state.token != ''"><strong>Profile</strong></router-link>
      </div>
    </div>
    <router-view />
  </div>
</template>
<style>



#app {
  background-color: #F1DEFA;
  font-family: 'Quicksand', sans-serif;
  min-height: 100vh;
  padding: 0;
}

#nav {
  font-size: 20px;
  padding: 10px;
  margin-bottom: 5px;
  background-color: #0E2E5B;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#nav a {
  background-color: #f1defa;
  margin-left: 20px;
  padding: 10px 20px;
  color: #0E2E5B;
  border-radius: 5px;
  text-decoration: none;
  display: inline-block;

}
#icon {
  height: 50px;
  align-items: center;
  margin-right: 10px
}
#title {
  height: 50px;
  margin-right: 10px
}
#slogan {
  height: 40px;
  padding-bottom: 5px
}

a:link, a:visited, a:hover, a:active {
  color: #0E2E5B;
}

</style>
