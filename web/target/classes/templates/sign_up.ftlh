<#import "spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Регистрация</title>
  <link rel="stylesheet" href="/css/style.css">
  <style>
    .error {
      color: red;
    }
  </style>

</head>
<body>


<section class="login__section">
  <header class="login__header">
    <div class="logo__login">
      <h2 class="logo__header__text">Trello</h2>
    </div>
  </header>
  <div class="login__content">
    <@spring.bind "signUpForm"/>
    <form class="login__form" method="post">
      <h1 class="login__form--title">Вход в Trello</h1>
      <div class="form__group">
        <@spring.formInput path="signUpForm.email" attributes="placeholder='Укажите адрес электронной почты' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
        <@spring.formInput path="signUpForm.name" attributes="placeholder='Укажите имя и фамилию' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
        <@spring.formPasswordInput path="signUpForm.password" attributes="placeholder='Придумайте пароль' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
        <@spring.formPasswordInput path="signUpForm.verifyPassword" attributes="placeholder='Введите пароль еще раз' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <button class="login__btn--input">Зарегистрироваться</button>
    </form>
  </div>
</section>

</body>
</html>