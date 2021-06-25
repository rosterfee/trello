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
<#--      <div id="error" class="error__block">-->
<#--        <p class="error__message">-->
<#--          Аккаунт с таким адресом электронной почты не существует-->
<#--        </p>-->
<#--      </div>-->
      <h1 class="login__form--title">Вход в Trello</h1>
      <div class="form__group">
<#--        <input type="email" name="email" class="login__form--field"-->
<#--               placeholder="Укажите адрес электронной почты">-->
        <@spring.formInput path="signUpForm.email" attributes="placeholder='Укажите адрес электронной почты' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
<#--        <input type="text" name="name" class="login__form--field" placeholder="Введите полное имя">-->
        <@spring.formInput path="signUpForm.name" attributes="placeholder='Укажите имя и фамилию' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
<#--        <input type="password" name=password class="login__form--field" placeholder="Введите пароль">-->
        <@spring.formPasswordInput path="signUpForm.password" attributes="placeholder='Придумайте пароль' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <div class="form__group">
        <#--        <input type="password" name=password class="login__form--field" placeholder="Введите пароль">-->
        <@spring.formPasswordInput path="signUpForm.verifyPassword" attributes="placeholder='Введите пароль еще раз' class='login__form--field'"/>
        <@spring.showErrors separator="<br>" classOrStyle="error"/>
      </div>
      <button class="login__btn--input">Зарегистрироваться</button>
    </form>
  </div>
</section>

</body>
</html>