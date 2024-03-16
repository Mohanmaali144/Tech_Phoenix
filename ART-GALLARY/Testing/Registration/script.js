// invalid fields
(function () {
  "use strict";

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll(".needs-validation");

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener(
      "submit",
      function (event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }

        form.classList.add("was-validated");
      },
      false
    );
  });
})();
// ----end

//   ================>>>Profile <<<===================

document.addEventListener("change", function (event) {
  if (event.target.classList.contains("uploadProfileInput")) {
    var triggerInput = event.target;
    var currentImg = triggerInput.closest(".pic-holder").querySelector(".pic")
      .src;
    var holder = triggerInput.closest(".pic-holder");
    var wrapper = triggerInput.closest(".profile-pic-wrapper");

    var alerts = wrapper.querySelectorAll('[role="alert"]');
    alerts.forEach(function (alert) {
      alert.remove();
    });

    triggerInput.blur();
    var files = triggerInput.files || [];
    if (!files.length || !window.FileReader) {
      return;
    }

    if (/^image/.test(files[0].type)) {
      var reader = new FileReader();
      reader.readAsDataURL(files[0]);

      reader.onloadend = function () {
        holder.classList.add("uploadInProgress");
        holder.querySelector(".pic").src = this.result;

        var loader = document.createElement("div");
        loader.classList.add("upload-loader");
        loader.innerHTML =
          '<div class="spinner-border text-primary" role="status"><span class="sr-only">Loading...</span></div>';
        holder.appendChild(loader);

        setTimeout(function () {
          holder.classList.remove("uploadInProgress");
          loader.remove();

          var random = Math.random();
          if (random < 0.9) {
            wrapper.innerHTML +=
              '<div class="snackbar show" role="alert"><i class="fa fa-check-circle text-success"></i> Profile image updated successfully</div>';
            triggerInput.value = "";
            setTimeout(function () {
              wrapper.querySelector('[role="alert"]').remove();
            }, 3000);
          } else {
            holder.querySelector(".pic").src = currentImg;
            wrapper.innerHTML +=
              '<div class="snackbar show" role="alert"><i class="fa fa-times-circle text-danger"></i> There is an error while uploading! Please try again later.</div>';
            triggerInput.value = "";
            setTimeout(function () {
              wrapper.querySelector('[role="alert"]').remove();
            }, 3000);
          }
        }, 1500);
      };
    } else {
      wrapper.innerHTML +=
        '<div class="alert alert-danger d-inline-block p-2 small" role="alert">Please choose a valid image.</div>';
      setTimeout(function () {
        var invalidAlert = wrapper.querySelector('[role="alert"]');
        if (invalidAlert) {
          invalidAlert.remove();
        }
      }, 3000);
    }
  }
});

// Vanilla Script Version
document.addEventListener("change", function (event) {
  if (event.target.classList.contains("uploadProfileInput")) {
    var triggerInput = event.target;
    var currentImg = triggerInput.closest(".pic-holder").querySelector(".pic")
      .src;
    var holder = triggerInput.closest(".pic-holder");
    var wrapper = triggerInput.closest(".profile-pic-wrapper");

    var alerts = wrapper.querySelectorAll('[role="alert"]');
    alerts.forEach(function (alert) {
      alert.remove();
    });

    triggerInput.blur();
    var files = triggerInput.files || [];
    if (!files.length || !window.FileReader) {
      return;
    }

    if (/^image/.test(files[0].type)) {
      var reader = new FileReader();
      reader.readAsDataURL(files[0]);

      reader.onloadend = function () {
        holder.classList.add("uploadInProgress");
        holder.querySelector(".pic").src = this.result;

        var loader = document.createElement("div");
        loader.classList.add("upload-loader");
        loader.innerHTML =
          '<div class="spinner-border text-primary" role="status"><span class="sr-only">Loading...</span></div>';
        holder.appendChild(loader);

        setTimeout(function () {
          holder.classList.remove("uploadInProgress");
          loader.remove();

          var random = Math.random();
          if (random < 0.9) {
            wrapper.innerHTML +=
              '<div class="snackbar show" role="alert"><i class="fa fa-check-circle text-success"></i> Profile image updated successfully</div>';
            triggerInput.value = "";
            setTimeout(function () {
              wrapper.querySelector('[role="alert"]').remove();
            }, 3000);
          } else {
            holder.querySelector(".pic").src = currentImg;
            wrapper.innerHTML +=
              '<div class="snackbar show" role="alert"><i class="fa fa-times-circle text-danger"></i> There is an error while uploading! Please try again later.</div>';
            triggerInput.value = "";
            setTimeout(function () {
              wrapper.querySelector('[role="alert"]').remove();
            }, 3000);
          }
        }, 1500);
      };
    } else {
      wrapper.innerHTML +=
        '<div class="alert alert-danger d-inline-block p-2 small" role="alert">Please choose a valid image.</div>';
      setTimeout(function () {
        var invalidAlert = wrapper.querySelector('[role="alert"]');
        if (invalidAlert) {
          invalidAlert.remove();
        }
      }, 3000);
    }
  }
});


// ==========================validation================================
function validateForm() {

  let fullname = document.getElementById("fullname");
  let username = document.getElementById("username");
  let email = d
  let mobile;
  let dob;
  let city;
  let zip;
  let instaurl
  let fburl;
  let giturl;
  let linkrdin;
  let bio;

  return false;
}
// ========chat

// Function to validate the form
function validateForm() {
  // Validation for Full Name
  var fullName = document.getElementById("fname").value;
  var fullNameError = document.getElementById("fullname-error");
  if (!/^[A-Za-z\s]{3,}$/.test(fullName)) {
    fullNameError.textContent = "Please enter a valid Full Name";
    return false;
  } else {
    fullNameError.textContent = "";
  }

  // Validation for Username
  var username = document.getElementById("username").value;
  var usernameError = document.getElementById("username-error");
  if (!/^[A-Za-z\d]+@[a-z0-9.\-]{3,}$/.test(username)) {
    usernameError.textContent = "Please enter a valid Username ";
    return false;
  } else {
    usernameError.textContent = "";
  }

  // Validation for Email
  var email = document.getElementById("email").value;
  var emailPattern = /^[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
  var emailError = document.getElementById("email-error");
  if (!emailPattern.test(email)) {
    emailError.textContent = "Please enter a valid Email address.";
    return false;
  } else {
    emailError.textContent = "";
  }


 // Validation for Mobile
// Validation for Mobile
var mobile = document.forms[0]["mobile"].value;
var mobileError = document.getElementById("mobile-error");

console.log("Mobile value:", mobile);

if ( /^\d+$/.test(mobile)) {
  mobileError.textContent = "Please enter a valid numeric Mobile number.";
  return false;
} else {
  mobileError.textContent = "";
}



  var zip = document.getElementById("zip").value;
  var zipError = document.getElementById("zip-error");
  if (!/^\d{6}$/.test(zip)) {
    zipError.textContent = "Please enter a valid zip number";
    return false;
  } else {
    zipError.textContent = "";
  }

  
  var city = document.getElementById("city").value;
  var cityError = document.getElementById("city-error");
  if (!/^\d{6}$/.test(city)) {
  cityError.textContent = "Please enter a valid zip number";
    return false;
  } else {
    cityError.textContent = "";
  }

  // Validation for Date of Birth
var dob = document.getElementById("dob").value;
var dobError = document.getElementById("dob-error");

// Regular expression for YYYY-MM-DD format
var dobRegex = /^\d{4}-\d{2}-\d{2}$/;
console.log("DOB value:", dob);

if (!dobRegex.test(dob)) {
  dobError.textContent = "Please enter a valid Date of Birth";
  return false;
} else {
  dobError.textContent = "";
}

  // Add similar validations for other fields...

  // If all validations pass, the form is submitted
  return true;
}

