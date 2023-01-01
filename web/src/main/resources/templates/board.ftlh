<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${board.name}</title>
    <link rel="stylesheet" href="/css/style.css">
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
</head>
<body onload="connect()">

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

<section class="main__section board__section">
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
            <button class="main__header--username">
                <#if user.avatar??>
                    <img src="${user.avatar}" alt="${user.initials}">
                <#else>
                    ${user.initials}
                </#if>
            </button>
        </div>
    </header>
    <main class="main__content--board">
        <header class="header__board">
            <div class="header__board--right">
                <div class="header__board-item">
                    Best_coder mainer
                </div>
                <div class="vertical__line"></div>
                <div class="header__board-team">Best coders</div>
                <div class="vertical__line"></div>
                <div class="header__board--members">
                    <div class="header__board--member-block admin">
                        <button class="board__avatar">
                            <#if user.avatar??>
                                <img src="${user.avatar}" alt="${user.initials}">
                            <#else>
                                ${user.initials}
                            </#if>
                        </button>
                        <span class="admin__symbol"></span>
                    </div>
                    <button class="board__avatar">
                        RM
                    </button>
                </div>

                <div class="vertical__line"></div>
                <div onclick="getUsersToInvite()" class="header__board-team" id="btnInvitation">Пригласить</div>
            </div>
            <div class="header__board--left">
                <button class="header__board-team header__board--menu" id="archiveBtn">Архив</button>
            </div>
        </header>

        <div class="lists__card" id="cards">
            <#list board.columns as column>
                <div id="column#${column.id}" class="card-list-container">
                    <header class="card__list--title">
                        ${column.name}
                        <svg onclick="deleteColumn(${column.id})" class="symbol__close--cardAdd">
                            <use xlink:href="#close"></use>
                        </svg>
                    </header>
                    <div id="cards${column.id}" class="card__list--cards">
                        <#list column.cards as card>
                            <div id="card-zone#${card.id}" class="card-zone">
                                <div class="card__zone-a" id="card#${card.id}">
                                    <div class="drag_start" draggable="true">
                                        <div class="card-container">
                                            <a class="card-body" href="/cards/${card.id}">
                                                <p class="card_name">
                                                    ${card.title}
                                                </p>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <svg onclick="deleteCard(${card.id})" class="symbol__close--cardAdd--">
                                    <use xlink:href="#close"></use>
                                </svg>
                            </div>
                        </#list>
                    </div>
                    <div class="card__list--footer notext-decoration">
                        <div class="card__textarea">
                            <input class="card__add--textarea" name="card__text" id="card__text"
                                      placeholder="Введите название карточки">
                            <div class="action__card">
                                <button id="add_card_button${column.id}" onclick="addCardFunc(${column.id})"
                                        class="card__add--btn">Добавить карточку
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
            <div class="card-list-container--add" id="add_column">
                <button class="card__list--addList">Добавить еще одну колонку</button>
                <div class="card__textarea">
                    <input class="card__add--textarea" name="card__text" id="card__text--list"
                              placeholder="Введите название колонки">
                    <div class="action__card">
                        <button onclick="addColumnFunc()" class="card__add--btn">Добавить колонку</button>
                    </div>
                </div>
            </div>

            <div class="page-content page-container" id="page-content">
                <div class="padding">
                    <div class="row container d-flex justify-content-center">
                        <div class="col-md-4">
                            <div class="box box-warning direct-chat direct-chat-warning">
                                <div class="box-body">
                                    <div id="messages-block" class="direct-chat-messages">

                                    </div>
                                </div>
                                <div class="box-footer">
                                    <form>
                                        <div class="input-group">
                                            <input id="text-input" type="text" name="message"
                                                   placeholder="Введите сообщение..." class="form-control">
                                            <span class="input-group-btn">
                                                <button onclick="sendMessage()" type="button"
                                                        class="btn btn-warning btn-flat">Отправить</button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <div class="menu__board" id="archiveBoard">
            <div class="menu__board--title">
                Архив
                <svg class="symbol__close--menu">
                    <use xlink:href="#close"></use>
                </svg>
            </div>
            <div class="menu__board--content">
                <div class="menu__board--item">
                    <a href="#" class="menu__board--card notext-decoration">
                        <span class="archive__name">Cоздание регистрации</span>
                    </a>
                    <p class="menu__board--action">
                        <a href="#" class="link__back">Вернуть на доску</a>
                        <span class="notext-decoration delitel"> - </span>
                        <a href="#" class="link__delete">Удалить</a>
                    </p>
                </div>
            </div>
        </div>

    </main>

    <div class="header__board--popup" id="popup_member">
        <div class="header__board--title">
            <button class="board__avatar--popup">
                <#if user.avatar??>
                    <img src="${user.avatar}" alt="${user.initials}">
                <#else>
                    ${user.initials}
                </#if>
            </button>
            <div class="header__popup--info">
                <span class="header__popup--username">${user.name}</span>
                <span class="header__popup--email">${user.email}</span>
            </div>

            <svg class="symbol__close--popup">
                <use xlink:href="#close"></use>
            </svg>
        </div>
        <div class="header__popup--content">
            <a href="#" class="header__popup--a">Удалить с доски...</a>
        </div>
    </div>

    <div class="popup__section--invitation">
        <div class="popup__invitation">
            <svg class="symbol__close--invitation">
                <use xlink:href="#close"></use>
            </svg>
            <div class="popup__invitation--title">
                Пригласить на доску
            </div>
            <form class="popup__invitation--form">
                <div id="popup__invitation--group" class="popup__invitation--group">
                    <input id="invite-input" type="text" placeholder="Адрес электронной почты"
                           class="popup__invitation--input">
                </div>
                <button class="popup__invitation--btn">Отправить приглашение</button>
            </form>
        </div>
    </div>
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
                <img src="${user.avatar}" alt="${user.initials}">
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://www.jsviews.com/download/jsrender.min.js"></script>

<script type="text/javascript">

    window.onload = dragAndDrop

    const menu = $('.menu__section');
    const btnMenu = $('.main__header--username');
    const closeBtnMenu = $('.symbol__close');
    const memberBoard = $('#popup_member');
    const btnMember = $('.board__avatar');
    const closePopupMember = $('.symbol__close--popup');
    const popupInvitation = $('.popup__section--invitation');
    const btnPopupInvitation = $('#btnInvitation');
    const closePopupInvitation = $('.symbol__close--invitation');
    const archive = $('#archiveBoard');
    const btnArchive = $('#archiveBtn');
    const closeArchive = $('.symbol__close--menu');
    const exData = {
        address: {
            street: 'Main Street'
        }
    };
    const addCardBtn = $('#add_card_btn')

    btnMenu.click(function () {
        memberBoard.removeClass('popup__active');
        menu.toggleClass('menu__active');
    });

    closeBtnMenu.click(function () {
        menu.removeClass('menu__active');
    });

    btnMember.click(function () {
        memberBoard.toggleClass('popup__active');
    });

    closePopupMember.click(function () {
        memberBoard.removeClass('popup__active');
    });

    btnPopupInvitation.click(function () {
        memberBoard.removeClass('popup__active');
        menu.removeClass('menu__active');
        popupInvitation.addClass('active__invitation');
    });
    closePopupInvitation.click(function () {
        popupInvitation.removeClass('active__invitation');
    });

    btnArchive.click(function () {
        archive.addClass('active__menu');
    });

    closeArchive.click(function () {
        archive.removeClass('active__menu');
    });

    function openCard(id) {
    }

    async function addCardFunc(column_id) {

        let parent = document.getElementById('add_card_button' + column_id).parentNode.parentNode;
        let input = parent.firstElementChild;
        let title = input.value
        input.value = null

        let card = {
            "title": title,
            "columnId": column_id
        }

        let response = await fetch('/cards/add_card', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(card)
        })

        if (response.ok) {

            let result = await response.json()
            let card_id = result.id

            console.log(card_id)

            let cardsHolder = document.getElementById('cards' + column_id);
            let cardBlock = document.createElement('div')
            cardBlock.className = 'card-zone'
            cardBlock.id = 'card-zone#' + card_id

            cardBlock.innerHTML = `<div class="card__zone-a" id="card#` + card_id + `">
                                    <div class="drag_start" draggable="true">
                                        <div class="card-container">
                                            <a href="/cards/` + card_id + `" class="card-body">
                                                <p class="card_name">
                                                    ` + title + `
                                                </p>
                                            </a>
                                        </div>
                                    </div>
                                </a>
                                <svg onclick="deleteCard(` + card_id + `)" class="symbol__close--cardAdd--">
                                    <use xlink:href="#close"></use>
                                </svg>`

            cardBlock.ondragstart = drag

            cardsHolder.appendChild(cardBlock)
            await dragAndDrop()
        }

    }

    async function addColumnFunc() {

        let input = document.getElementById('card__text--list')
        let name = input.value
        input.value = null;

        let column = {
            "name": name,
            "boardId": ${board.id}
        }

        let response = await fetch('/columns/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(column)
        })

        if (response.ok) {

            let result = await response.json();
            let column_id = result.id


            let parent = document.getElementById('cards')
            let newColumnBlock = document.createElement('div')
            newColumnBlock.classList = 'card-list-container'
            newColumnBlock.id = 'column#' + column_id
            newColumnBlock.innerHTML =
                `
                    <header class="card__list--title">` + name + `
                        <svg onclick="deleteColumn(` + column_id + `)" class="symbol__close--cardAdd">
                            <use xlink:href="#close"></use>
                        </svg>
                    </header>
                    <div id="cards` + column_id + `" class="card__list--cards"></div>
                    <div class="card__list--footer notext-decoration">
                        <div class="card__textarea">
                            <input class="card__add--textarea" name="card__text" id="card__text"
                                      placeholder="Введите название карточки">
                            <div class="action__card">
                                <button id="add_card_button` + column_id + `" onclick="addCardFunc(` + column_id + `)"
                                class="card__add--btn">Добавить карточку</button>
                            </div>
                        </div>
                    </div>
               `

            let cardsList = newColumnBlock.querySelector('.card__list--cards')
            cardsList.ondragover = allowDrop
            cardsList.ondrop = drop

            parent.insertBefore(newColumnBlock, document.querySelector('#add_column'))
        }
    }

    let stompClient = null

    function connect() {
        const socket = new SockJS('/ws')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, frame => {
            console.log('Connected: ' + frame)
            stompClient.subscribe('/chat/get_message', content => {

                let payload = JSON.parse(content.body)

                if (payload.boardId == `${board.id}`) {

                    let message = document.createElement("div")
                    let html = `<div class="direct-chat-msg">
                                                <div class="direct-chat-info clearfix">
                                                    <span class="direct-chat-name pull-left">` + payload.name + `</span>
                                                    <span class="direct-chat-timestamp pull-right">` + payload.date + `</span>
                                                </div>`
                    if (payload.avatar != null) {
                        html += `<img class="direct-chat-img" src="` + payload.avatar + `" alt="">`
                    } else {
                        html = html + `<div>` + payload.initials + `</div>`
                    }
                    html += `<div class="direct-chat-text">` + payload.text + `</div>`


                    message.innerHTML = html

                    let messagesBlock = document.getElementById("messages-block")
                    messagesBlock.appendChild(message)

                }
            })
        })
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect()
        }
        console.log("Disconnected")
    }

    function sendMessage() {
        let text = document.getElementById("text-input").value

        let message = {
            'text': text,
            'boardId': `${board.id}`,
            'authorId': `${user.id}`
        }

        stompClient.send("/app/send_message", {}, JSON.stringify(message))
    }

    function getUsersToInvite() {
        let inviteInput = document.getElementById("invite-input")
        inviteInput.addEventListener(`input`, async function printUsersToInvite() {

            let inviteList = document.getElementById("searched-list")
            if (inviteList != null) {
                inviteList.remove()
            }

            let email = inviteInput.value
            let response = await fetch('/boards/get_users_to_inv/' + email)

            if (response.ok) {
                let users = await response.json()
                let len = users.length
                if (len > 0) {

                    let parent = document.getElementById("popup__invitation--group")
                    inviteList = document.createElement("div")
                    inviteList.innerHTML = `<div id="searched-list" class="popup__invitation--searched"></div>`
                    parent.appendChild(inviteList)

                    let user
                    let userInfo
                    let html
                    for (let i = 0; i < len; i++) {
                        user = users[i]
                        userInfo = document.createElement("div")
                        html = `<a href="#" class="popup__invitation--item notext-decoration">
                            <div class="main__header--username board__avatar">`
                        <#if user.avatar??>
                        html += `<img src="` + user.avatar + `" alt=""/>`
                        <#else>
                        html += user.initials
                        </#if>
                        html += `</div>`
                        html += `<div class="popup__invitation--email">` + user.email + `</div>
                        </a>`
                        userInfo.innerHTML = html

                        inviteList.appendChild(userInfo)
                    }
                }
            }
        })
    }

    async function deleteColumn(id) {
        document.getElementById('column#' + id).remove()
        await fetch('/columns/delete/' + id, {
            method: 'DELETE'
        })
    }

    async function deleteCard(id) {
        document.getElementById('card#' + id).remove()
        await fetch('/cards/delete/' + id, {
            method: 'DELETE'
        })
    }

    async function dragAndDrop() {

        const columns = document.querySelectorAll('.card-list-container')
        const cards = document.querySelectorAll('.card-zone')
        columns.forEach((column,item) => {
                let index = item
                console.log(item)
                column.addEventListener('drop', async function (event,index) {
                event.preventDefault()
                event.stopPropagation()

                let dataTransfer = event.dataTransfer

                let cardId = dataTransfer.getData('cardId')
                let card = document.getElementById(cardId)
                console.log(card)
                console.log(columns)
                console.log(item)
                console.log(columns[item])

                let oldColumnId = dataTransfer.getData('oldColumnId')
                let oldColumn = document.getElementById(oldColumnId)

                console.log('oldColumn: ' + oldColumnId)
                oldColumn.removeChild(card)

                console.log('card: ' + cardId)
                console.log('target: ' + event.target)
                columns[item].querySelector('.card__list--cards').append(card);
                card.querySelector('.drag_start').style.removeProperty('display')
                console.log(event.target)
                console.log('newColumnId' + event.target.id.slice(5))
                let newColumnId = columns[item].id.slice(7)
                cardId = cardId.slice(10)

                document.getElementById('card#' + cardId).style.removeProperty('display')

                console.log('/cards/move/' + cardId + '/' + newColumnId)
                  await fetch('/cards/move/' + cardId + '/' + newColumnId,
                    {
                        method: 'PATCH'
                    })
            })
            column.ondragover = allowDrop
            column.ondragenter = allowDrop
        })

        cards.forEach(card => {
            card.ondragstart = drag
        })

    }

    function allowDrop(event) {
        console.log('hhhip')
        event.preventDefault()
        event.stopPropagation()
    }

    async function drop(event,column) {
        event.preventDefault()
        event.stopPropagation()

        let dataTransfer = event.dataTransfer

        let cardId = dataTransfer.getData('cardId')
        let card = document.getElementById(cardId)
        console.log(card)
        console.log(column)

        let oldColumnId = dataTransfer.getData('oldColumnId')
        let oldColumn = document.getElementById(oldColumnId).querySelector('.card__list--cards')

        console.log('oldColumn: ' + oldColumnId)
        oldColumn.removeChild(card)

        console.log('card: ' + cardId)
        console.log('target: ' + event.target)
        await event.target.append(card)
        console.log(event.target)
        console.log('newColumnId' + event.target.id.slice(5))
        let newColumnId = event.target.id.slice(5)
        cardId = cardId.slice(10)

        document.getElementById('card#' + cardId).style.removeProperty('display')

        console.log('/cards/move/' + cardId + '/' + newColumnId)
        await fetch('/cards/move/' + cardId + '/' + newColumnId,
            {
                method: 'PATCH'
            })
    }

    function drag(event) {

        let card = event.target

        setTimeout(() => {
            card.style.display = 'none'
        }, 0)

        let dataTransfer = event.dataTransfer
        dataTransfer.dropEffect = 'move'
        dataTransfer.effectAllowed = 'move'
        dataTransfer.setData('cardId', card.parentNode.parentNode.id)
        dataTransfer.setData('oldColumnId', card.parentNode.parentNode.parentNode.id)
    }

</script>
</body>
</html>