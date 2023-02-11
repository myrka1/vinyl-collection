<template>
  <div id="register" class="text-center">
    <h1 id="welcome" class="h3 mb-3 font-weight-normal">Create Acc<img id="record" src="../assets/vinyl-icon.png">unt!</h1>
      <img src="../assets/vr-logo.png" id="logo">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Please Register Below</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="input-container">
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
      </div>
      <br>
      <div class="input-container">
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      </div>
      <br>
      <div class="input-container">
      <label for="confirmPassword" class="sr-only">Confirm Password</label>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      </div>
      <br>
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <nav id="links">
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      </nav>
      
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
#register {
  display: grid;
  grid-template-columns: 50vw 50vw;
  grid-template-rows: 25vh 50vh 25vh;
  grid-template-areas:
  "greeting greeting"
  "logo register"
  ;
  gap: 10px;
  height: 100vh;
  justify-content: space-evenly;
  align-content: space-evenly;
  color: #0E2E5B;

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

.form-register {
  grid-area: register;
  justify-self: left;
  align-self: center;

  height: 75%;
  padding: 10px
}
#links {
  font-size: 20px;
  align-self: end;
  margin-top: 20px;
  margin-bottom: 20px;
}

.input-containers {
  height: 20px;
}
#username {
  margin-left: 10px;
  height: 20px; 
}

#password {
  margin-left: 10px;
  height: 20px
}

#confirmPassword {
  margin-left: 10px;
  height:20px
}

button {
  height: 25px;
}


</style>
