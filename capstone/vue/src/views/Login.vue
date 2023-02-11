<template>
  <div id="login" class="text-center">
    <h1 id="welcome">Welc<img id="record" src="../assets/vinyl-icon.png">me Back!</h1>
    <img src="../assets/vr-logo.png" id="logo">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <br>
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      
      <button type="submit">Sign in</button>
    <nav id="links">
     <router-link :to="{ name: 'register' }">Need an account?</router-link>
    </nav>
    </form>
    
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/profile");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style scoped>
#login {
  display: grid;
  grid-template-columns: 50vw 50vw;
  grid-template-rows: 25vh 50vh 25vh;
  grid-template-areas:
  "greeting greeting"
  "logo login"
  ;
  gap: 10px;
  /* height: 100vh; */
  justify-content: space-evenly;
  align-content: space-evenly;
  color: #0E2E5B

}
#welcome {
  grid-area: greeting;
  justify-self: center;
  align-self: end;
  font-size: 100px;
}

#record {
  width: 50px;
  height: 50px;
  animation-name: spin;
  animation-duration: 1000ms;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
  border-radius: 50%;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

#logo {
  grid-area: logo;
  height: 75%;
  justify-self: right;

}

.form-signin {
  grid-area: login;
  justify-self: left;
  align-self: center;

  height: 75%;
  padding: 10px
}
#links {
  font-size: 20px;
  align-self: end;
  margin-top: 20px;
}

#username {
  margin: 10px;
  height: 20px; 
}

#password {
  margin: 10px;
  height: 20px
}

button {
  height: 25px;
}

h1 {
  user-select: none;
}

</style>