<#import "spring.ftl" as spring/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/style.css">
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>

<svg style="display: none">
    <symbol id="plus" viewBox="0 0 426.66667 426.66667">
        <g>
            <path
                    d="m405.332031 192h-170.664062v-170.667969c0-11.773437-9.558594-21.332031-21.335938-21.332031-11.773437 0-21.332031 9.558594-21.332031 21.332031v170.667969h-170.667969c-11.773437 0-21.332031 9.558594-21.332031 21.332031 0 11.777344 9.558594 21.335938 21.332031 21.335938h170.667969v170.664062c0 11.777344 9.558594 21.335938 21.332031 21.335938 11.777344 0 21.335938-9.558594 21.335938-21.335938v-170.664062h170.664062c11.777344 0 21.335938-9.558594 21.335938-21.335938 0-11.773437-9.558594-21.332031-21.335938-21.332031zm0 0"/>
        </g>
    </symbol>

    <symbol id="close" viewBox="0 0 14 14">
        <path fill-rule="evenodd" clip-rule="evenodd"
              d="M5.58579 7L0.292893 1.70711C-0.0976311 1.31658 -0.0976311 0.683418 0.292893 0.292893C0.683418 -0.0976311 1.31658 -0.0976311 1.70711 0.292893L7 5.58579L12.2929 0.292893C12.6834 -0.0976311 13.3166 -0.0976311 13.7071 0.292893C14.0976 0.683418 14.0976 1.31658 13.7071 1.70711L8.41421 7L13.7071 12.2929C14.0976 12.6834 14.0976 13.3166 13.7071 13.7071C13.3166 14.0976 12.6834 14.0976 12.2929 13.7071L7 8.41421L1.70711 13.7071C1.31658 14.0976 0.683418 14.0976 0.292893 13.7071C-0.0976311 13.3166 -0.0976311 12.6834 0.292893 12.2929L5.58579 7Z"
        ></path>
    </symbol>
</svg>
<section class="main__section">
    <header class="main__header">
        <div class="main__header--right">
            <button class="main__header--block">
                Дом
            </button>
            <button class="main__header--dashboard">
                <span class="header__dashboard-text">Доски</span>
            </button>
        </div>
        <div class="main__header--middle">
            Trello
        </div>
        <div class="main__header--left">
                <button class="main__header--create">
                    Создать доску
                </button>
            <button class="main__header--username">
                <#if user.avatar??>
                    <img src="${user.avatar}" alt="">
                <#else>
                    ${user.initials}
                </#if>
            </button>
        </div>
    </header>
    <section class="main__content">
        <div class="main__content--left">
            <button class="main__content--item">
                <span class="main__content--text">Доски</span>
            </button>
            <div class="main__content--block">
                <div class="main__content--title">
                    <span class="main__content--titleText">Команды</span>
                    <button class="main__content--add">
                        <svg class="symbol__plus">
                            <use xlink:href="#plus"></use>
                        </svg>
                    </button>
                </div>
                <button class="main__content--item">
                    <span class="main__content--text">Best Coders</span>
                </button>
            </div>
        </div>


        <div class="main__content--right">
            <div class="main__content--group">
                <div class="content__group--title">
                    <span class="content__group--text">Мои доски</span>
                </div>
                <div class="content__group--cards">
                    <#list boards as board>
                        <a href="/boards/${board.name}" class="content__group--card notext-decoration">
                            ${board.name}
                        </a>
                    </#list>
                    <a href="#" class="content__group--card notext-decoration content__group_create__card">
                        Создать доску
                    </a>
                </div>
            </div>
        </div>
    </section>
    <form action="33666" id="wer"></form>
</section>

<section class="menu__section">
    <header class="menu__header">
        Учетная запись
        <svg class="symbol__close">
            <use xlink:href="#close"></use>
        </svg>
    </header>
    <div class="menu__user">
        <button class="main__header--username menu__user--avatar">
            <#if user.avatar??>
                <img src="${user.avatar}" alt="">
            <#else>
                ${user.initials}
            </#if>
        </button>
        <div class="menu__user--info">
            <div class="menu__user-name">${user.name}</div>
            <div class="menu__user-email">${user.email}</div>
        </div>
    </div>
    <ul class="menu__user--ul">
        <li class="menu__user--li">Выйти</li>
    </ul>
</section>

<div id="createTeam" class="create_team--window">
    <div class="create_team--modal">
        <svg class="symbol__close--modal">
            <use xlink:href="#close"></use>
        </svg>
        <div class="team__modal--info">
            <form class="team__modal--form">
                <span class="team__modal--title">Создайте команду</span>
                <span class="team__modal--text">Повысьте производительность: участники команды смогут получать удобный доступ ко всем доскам.</span>
                <div class="modal__group">
                    <label for="name_team" class="modal__team--label">Имя команды</label>
                    <input type="text" id="name_team" class="modal__team--input" placeholder="Введите название команды">
                    <div class="modal__team-undertext">Укажите название вашей команды, компании или организации.</div>
                </div>

                <button class="modal__team--btn">Создать команду</button>
            </form>
        </div>
    </div>
</div>

<div class="create_team--window" id="createBoard">
    <div class="create_team--modal">
        <svg class="symbol__close--modal">
            <use xlink:href="#close"></use>
        </svg>
        <div class="team__modal--info">
            <@spring.bind "boardCreatingForm"/>
            <form class="team__modal--form" action="/boards/create" method="POST">
                <span class="team__modal--title">Создайте доску</span>
                <span class="team__modal--text">Повысьте удобство: создайте доску для комфортной работы.</span>
                <div class="modal__group">
                    <label for="name_board" class="modal__team--label">Название доски</label>
                    <@spring.formInput path="boardCreatingForm.name" attributes="placeholder='Введите название доски' class='modal__team--input'"/>
                    <@spring.showErrors separator="<br>" classOrStyle="error"/>
                    <div class="modal__team-undertext">Укажите название доски.</div>
                    <#if errors??>
                        <#list errors as error>
                            <div style="color: red">
                                ${error}
                            </div>
                        </#list>
                    </#if>
                </div>

                <div class="modal__group">
                    <label for="name_team" class="modal__team--label">Выберите тип доски</label>
                    <@spring.formSingleSelect path="boardCreatingForm.type" attributes="class='modal__team--input'" options=options />
                </div>

                <button type="submit" class="modal__team--btn">Создать доску</button>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    const menu = $('.menu__section');
    const btnMenu = $('.main__header--username');
    const btnPlus = $('.main__content--add');
    const teamWindow = $('#createTeam');
    const closeBtn = $('.symbol__close--modal');
    const closeBtnMenu = $('.symbol__close');
    const boardModal = $('#createBoard');
    const btnCreateBoard = $('.main__header--create');

    btnMenu.click(function () {
        menu.toggleClass('menu__active');
    });

    btnPlus.click(function () {
        menu.removeClass('menu__active');
        teamWindow.addClass('team__window--active');
    });

    closeBtn.click(function () {
        teamWindow.removeClass('team__window--active');
        boardModal.removeClass('team__window--active')
    });

    closeBtnMenu.click(function () {
        menu.removeClass('menu__active');
    });

    btnCreateBoard.click(function () {
        console.log(document.querySelector('#wer').attributes[0]);
        console.log(document.querySelector('#wer').attributes[0]);
        menu.removeClass('menu__active');
        boardModal.addClass('team__window--active');
    });
</script>
<script src="js/app.js"></script>
</body>
</html>