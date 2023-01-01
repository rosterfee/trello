<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>${card.title} на доске ${card.column.board.name}</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="card__content--window active__card--content">
  <div class="card__content">
    <header class="card__content--header">
      <svg class="symbol__monitor">
        <use xlink:href="#monitor"></use>
      </svg>
      <div class="card__content--infoCard">
        <span class="card__content--title">${card.title}</span>
        <span class="card__content--subtitle">в колонке
            <a href="#" class="notext-decoration">${card.column.name}</a>
          </span>
      </div>

      <svg class="symbol__close--content">
        <use xlink:href="#close"></use>
      </svg>
    </header>

    <div class="card__content--content">
      <div id="card-content" class="card__window--content">
        <div id="card__attachments--title" class="card__attachments--title">
          <svg class="symbol__attachments symbol__about">
            <use xlink:href="#about"></use>
          </svg>
          <span id="description-span" class="card__attachments--text">Описание</span>
          <#if card.description??>
            <button id="change-description-btn" onclick="updateDescription()" class="card__about--btn">Изменить</button>
          </#if>
        </div>

        <div id="card__about" class="card__about">
          <div id="card-about-text" class="card__about--text">
            <#if card.description??>
                ${card.description}
            <#else>
              Здесь должно быть описание
            </#if>
          </div>
          <#if !(card.description??)>
            <textarea id="card__about--text" placeholder="Введите описание" class="card__about--field" cols="30" rows="10"></textarea>
            <span id="save-description-span" class="card__about--action">
                <button id="save-description-btn" onclick="saveDescription()" class="card__about--save">Сохранить</button>
                  <svg class="symbol__close--about">
                    <use xlink:href="#close"></use>
                  </svg>
            </span>
          </#if>
        </div>

        <#if card.photos?size != 0>
          <div class="card__attachments--title">
            <svg class="symbol__attachments">
              <use xlink:href="#attachments"></use>
            </svg>
            <span class="card__attachments--text">Вложения</span>
          </div>
        </#if>

        <#if card.photos?size != 0>
          <#list card.photos as photo>
            <div class="card__attachments--items">
              <div class="card__attachments--item">
                <div class="card__attachments--icon">
                  <img src="${photo.content}" alt="../static/pictures/not-found-image-15383864787lu.jpg">
                </div>
                <div class="card__attachments--info">
                  <span class="card__attachments--name">${photo.title}</span>
                  <div class="card__attachments--time">
                    Добавлено ${photo.loadedAt}
                  </div>
                  <a href="#" class="card__attachments--delete">Удалить</a>
                </div>
              </div>
          </#list>
              <button class="card__attachments--add">
                Добавить вложение
              </button>
            </div>
        </#if>

        <#if card.checkLists?size != 0>
          <#list card.checkLists as checkList>
            <div id="card__attachments--title#${checkList.id}" class="card__attachments--title">
              <svg class="symbol__attachments">
                <use xlink:href="#checkList"></use>
              </svg>
              <span class="card__attachments--text">${checkList.title}</span>
              <button onclick="removeChecklist(${checkList.id})" class="card__about--btn">Удалить</button>
            </div>

            <div id="checklistTasks#${checkList.id}" class="card__about">
              <div id="card__check--items#${checkList.id}" class="card__check--items">
                <#list checkList.checkListTasks as task>
                  <div id="checklistTask#${task.id}" class="card__check--item">
                    <input id="task#${task.id}" onclick="changeTaskStatus(${task.id})" class="card__check--input" type="checkbox" <#if task.done> checked </#if>>
<#--                    <#if task.done == true>checked</#if>-->
                    <label  for="task#${task.id}" class="card__check--label">${task.title}</label>
                    <button onclick="removeChecklistTask(${task.id})" class="card__about--btn">Удалить</button>
                  </div>
                </#list>

                <button id="openCheckListTaskAddBtn#${checkList.id}" onclick="openCheckListTaskAdd(${checkList.id})" class="card__check--add">
                  Добавить элемент
                </button>

                <textarea style="display: none" id="card__check--text" placeholder="Введите элемент" class="card__check--field" cols="30" rows="10"></textarea>
                <span style="display: none" class="card__about--action">
                  <button class="card__about--save">Сохранить</button>
                  <svg class="symbol__close--about">
                    <use xlink:href="#close"></use>
                  </svg>
                </span>
              </div>
            </div>
          </#list>
        </#if>

        <div id="comments-block" class="card__attachments--title">
          <svg class="symbol__attachments">
            <use xlink:href="#chat"></use>
          </svg>
          <span class="card__attachments--text">Комментарии</span>
        </div>

        <div class="card__chat">
          <div class="card__chat--field">
            <div class="card__chat--avatar">
              <#if user.avatar??>
                <img src="${user.avatar}" alt="">
              <#else>
                ${user.initials}
              </#if>
            </div>
            <div class="card__chat--comment">
              <textarea id="add-comment-textarea" class="card__chat--textarea" placeholder="Напишите комментарий" cols="30" rows="10"></textarea>
              <span class="card__about--action card__chat--action">
                <button id="add-comment-btn" type="button" onclick="addComment()" class="card__about--save">Отправить</button>
                <svg class="symbol__close--about">
                  <use xlink:href="#alias"></use>
                </svg>
              </span>
            </div>
          </div>
          <div id="card__comments" class="card__comments">
            <#list card.comments as comment>
              <div id="card__comment#${comment.id}" class="card__comment">
                <div class="card__comment--descr">
                  <div class="card__chat--avatar card__comment--avatar">
                    <#if comment.author.avatar??>
                      <img src="${comment.author.avatar}" alt="">
                    <#else>
                      ${comment.author.initials}
                    </#if>
                  </div>
                  <div class="card__comment--info">
                    <span class="card__comment--username">${comment.author.name}</span>
                    <span class="card__comment--date">${comment.createdAt}</span>
                  </div>
                </div>
                <input id="comment-input#${comment.id}" disabled value="${comment.text}" class="card__comment--text">
                <div id="comment-action#${comment.id}" class="card__comment--action">
                  <#if user.id == comment.author.id>
                    <a href="#" onclick="changeComment(${comment.id})" class="card__comment--link notext-decoration">Изменить</a>
                    <a href="#" onclick="deleteComment(${comment.id})" class="card__comment--link notext-decoration">Удалить</a>
                  </#if>
                </div>
              </div>
            </#list>
          </div>
        </div>

      </div>
      <div id="panel" class="card__window--sidebar">
        <div class="card__sidebar--title">Добавить на карточку</div>
        <div class="card__sidebar--item">Участники</div>
        <div id="openCheckListAdd" onclick="openCheckListAdditionArea()" class="card__sidebar--item">Чек-лист</div>
        <div id="deadline" class="card__sidebar--item">Срок</div>
        <div class="card__sidebar--item">Вложение</div>
        <div class="card__sidebar--item">Архивация</div>
      </div>
    </div>
  </div>
</div>

<script>

    async function saveDescriptionRequest(text) {
      let body = {
        'text': text
      }
      return await fetch("/cards/save_description/${card.id}",
              {
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                method: 'POST',
                body: JSON.stringify(body)
              }
      )
    }

    async function saveDescription() {

      let text = document.getElementById("card__about--text").value

      let response = await saveDescriptionRequest(text)
      if (response.ok) {

        document.getElementById("save-description-span").remove()
        document.getElementById("card__about--text").remove()

        let parent = document.getElementById("card__attachments--title")

        let changeDescriptionButton = document.createElement("div")
        changeDescriptionButton.innerHTML = `<button id="change-description-btn"
        onclick="updateDescription()" class="card__about--btn">Изменить</button>`
        parent.appendChild(changeDescriptionButton)

        let cardAboutText = document.getElementById("card-about-text")
        if (cardAboutText != null) {
          cardAboutText.innerText = text
        } else {
          let cardAbout = document.getElementById("card__about")
          cardAboutText = document.createElement("div")
          cardAboutText.innerHTML = `<div id="card-about-text" class="card__about--text">` + text + `</div>`
          cardAbout.appendChild(cardAboutText)
        }
      }

    }

    async function updateDescription() {

      document.getElementById("change-description-btn").remove()
      document.getElementById("card-about-text").remove()

      let parent = document.getElementById("card__about")

      let cardAboutText = document.createElement("div")
      cardAboutText.innerHTML = `<textarea id="card__about--text" placeholder="Введите описание" class="card__about--field" cols="30" rows="10"></textarea>`

      let saveDescriptionSpan = document.createElement("div")
      saveDescriptionSpan.innerHTML = `<span id="save-description-span" class="card__about--action">
              <button id="save-description-btn" onclick=saveDescription() class="card__about--save">Сохранить</button>
                <svg class="symbol__close--about">
                  <use xlink:href="#close"></use>
                </svg>
          </span>`

      parent.appendChild(cardAboutText)
      parent.appendChild(saveDescriptionSpan)
    }

    async function addComment() {

      let text = document.getElementById("add-comment-textarea").value

      let body = {
        'authorId': `${user.id}`.replaceAll(/\s+/g, ''),
        'text': text,
        'cardId': `${card.id}`
      }

      let response = await fetch('/comments/add',
              {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
              }
      )

      if (response.ok) {

        let result = await response.json()
        let commentId = await result.id
        let createdAt = await result.date
        let authorName = `${user.name}`

        let comment = document.createElement("div")

        let html = `<div id="card__comment#` + commentId + `" class="card__comment">
                <div class="card__comment--descr">
                  <div class="card__chat--avatar card__comment--avatar">`

        <#if user.avatar??>
          let userAvatar = `${user.avatar}`
            html += `<img src="` + userAvatar + `" alt="">`
        <#else>
          let initials = `${user.initials}`
          html += initials
        </#if>

        html += `</div>
                  <div class="card__comment--info">
                    <span class="card__comment--username">` + authorName + `</span>
                    <span class="card__comment--date">` + createdAt + `</span>
                  </div>
                </div>
                <input id="comment-input#` + commentId + `" value="` + text + `" disabled class="card__comment--text">
                <div id="comment-action#` + commentId + `" class="card__comment--action">
                  <a href="#" onclick="changeComment(` + commentId + `)" class="card__comment--link notext-decoration">Изменить</a>
                  <a href="#" onclick="deleteComment(` + commentId + `)" class="card__comment--link notext-decoration">Удалить</a>
                </div>
              </div>`

        comment.innerHTML = html

        let parent = document.getElementById("card__comments")
        parent.appendChild(comment)
      }
    }

    function openCheckListAdditionArea() {

      let panel = document.getElementById("panel")
      let deadline = document.getElementById("deadline")
      let inputAndBtn = document.createElement("div")
      inputAndBtn.innerHTML = `<div id="addCheckListForm">
        <input id="checkListTitleInput" type="text"/>
        <button type="button" onClick="addCheckList()">Добавить</button>
      </div>`

      panel.insertBefore(inputAndBtn, deadline)
    }

    async function addCheckList() {

      let inputText = document.getElementById("checkListTitleInput").value
      document.getElementById("addCheckListForm").remove()

      let body = {
        'text': inputText,
        'cardId': `${card.id}`
      }

      let response = await fetch('/checklists/add',
              {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
              }
      )

      if (response.ok) {

        let result = await response.json()
        let checkListId = result.id

        let commentsBlock = document.getElementById("comments-block")
        let parent = commentsBlock.parentNode

        let newCheckList = document.createElement("div")
        newCheckList.innerHTML = `<div id="card__attachments--title#` + checkListId + `" class="card__attachments--title">
              <svg class="symbol__attachments">
                <use xlink:href="#checkList"></use>
              </svg>
              <span class="card__attachments--text">` + inputText + `</span>
              <button onclick="removeChecklist(` + checkListId + `)" class="card__about--btn">Удалить</button>
            </div>
            <div id="checklistTasks#` + checkListId + `" class="card__about">
              <div id="card__check--items#` + checkListId + `" class="card__check--items#">
                <button id="openCheckListTaskAddBtn#` + checkListId + `" onclick="openCheckListTaskAdd(` + checkListId + `)" class="card__check--add">
                      Добавить элемент
                </button>
              </div>
            </div>`

        parent.insertBefore(newCheckList, commentsBlock)
      }
    }

    function openCheckListTaskAdd(checkListId) {

      console.log('id: ' + checkListId)

      let button = document.getElementById("openCheckListTaskAddBtn#" + checkListId)
      let parent = button.parentNode

      let checkListTaskAddZone = document.createElement("div")
      checkListTaskAddZone.innerHTML = `<textarea id="card__check--text#` + checkListId + `" placeholder="Введите элемент" class="card__check--field" cols="30" rows="10"></textarea>
                <span id="addTaskSpan#` + checkListId + `" class="card__about--action">
                  <button type="button" onclick="addCheckListTask(` + checkListId + `)" class="card__about--save">Сохранить</button>
                  <svg class="symbol__close--about">
                    <use xlink:href="#close"></use>
                  </svg>
                </span>`

      parent.insertBefore(checkListTaskAddZone, button)
      button.remove()

    }

    async function addCheckListTask(checkListId) {

      console.log("check: " + checkListId)

      let checkListTitle = document.getElementById("card__check--text#" + checkListId).value

      document.getElementById("card__check--text#" + checkListId).remove()
      document.getElementById("addTaskSpan#" + checkListId).remove()

      let body = {
        'title': checkListTitle,
        'checkListId': checkListId
      }

      let response = await fetch('/checklists/tasks/add',
              {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
              }
      )
      if (response.ok) {

        let result = await response.json()
        let taskId = result.id

        let parent = document.getElementById("card__check--items#" + checkListId)
        console.log(parent.innerHTML)

        let newTask = document.createElement("div")
        newTask.innerHTML = `<div id="checklistTask#` + taskId + `" class="card__check--item">
                    <input id="task#` + taskId + `" class="card__check--input" type="checkbox" onclick="changeTaskStatus(` + taskId + `)">
                    <label for="task#` + taskId + `" class="card__check--label">` + checkListTitle + `</label>
                    <button onclick="removeChecklistTask(` + taskId + `)" class="card__about--btn">Удалить</button>
                  </div>`

        parent.appendChild(newTask)
        let button = document.createElement("div")
        button.innerHTML = `<div id="card__check--items#` + checkListId + `" class="card__check--items">
                <button id="openCheckListTaskAddBtn#` + checkListId + `" onclick="openCheckListTaskAdd(` + checkListId + `)" class="card__check--add">
                  Добавить элемент
                </button>
              </div>`
        parent.appendChild(button)

      }
    }

    async function changeTaskStatus(taskId) {

      let done = document.getElementById("task#" + taskId).checked

      let body = {
        taskId: taskId,
        done: done
      }

      await fetch('/checklists/tasks/changeStatus',
              {
                method: 'PATCH',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
              }
      )
    }

    async function removeChecklist(id) {

      document.getElementById('card__attachments--title#' + id).remove()
      document.getElementById('checklistTasks#' + id).remove()

      await fetch("/checklists/remove/" + id, {
        method: 'DELETE'
      })

    }

    async function deleteComment(id) {

      document.getElementById('card__comment#' + id).remove()

      await fetch("/comments/remove/" + id, {
        method: 'DELETE'
      })
    }

    function changeComment(id) {

      let input = document.getElementById('comment-input#' + id)
      input.disabled = false

      let commentActions = document.getElementById('comment-action#' + id)
      commentActions.innerHTML = ''

      let saveButton = document.createElement('div')
      saveButton.innerHTML = `<button id="change-comment-btn#` + id + `" type="button" onclick="commitCommentChange(` + id + `)" class="card__about--save">Сохранить</button>`
      commentActions.appendChild(saveButton)

    }

    async function commitCommentChange(id) {

      let input = document.getElementById('comment-input#' + id)
      input.disabled = true

      document.getElementById("change-comment-btn#" + id).remove()

      let commentActionsBlock = document.getElementById("comment-action#" + id)
      commentActionsBlock.innerHTML = `<a href="#" onclick="changeComment(` + id + `)" class="card__comment--link notext-decoration">Изменить</a>
                    <a href="#" onclick="deleteComment(` + id + `)" class="card__comment--link notext-decoration">Удалить</a>`

      let body = {
        'id': id,
        'text': input.value
      }

      await fetch('/comments/change',
              {
                method: 'PATCH',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
              })
    }

    async function removeChecklistTask(id) {
        console.log(document.getElementById("checklistTask#" + id))
        document.getElementById("checklistTask#" + id).remove()
        await fetch("/checklists/tasks/remove/" + id,
                {
                  method: 'DELETE',
                  headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                  }
        })
    }

</script>

</body>
</html>