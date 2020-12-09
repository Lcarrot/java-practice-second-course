<%@tag description="page's header" pageEncoding="UTF-8" %>

<header>
    <nav class="navbar navbar-expand-md navbar-expand-sm navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Flow Job</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-center" id="collapsibleNavbar">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Лучшие предложения</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Мой профиль</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"> Быстрые заказы </a>
                </li>
            </ul>
            <div class="btn-group mr-2" role="group">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                </form>
            </div>
        </div>
    </nav>
</header>

<style>
    <%@include file="../../views/css/index.css" %>
</style>