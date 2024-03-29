<%@ taglib prefix="commonTags" uri="/WEB-INF/tlds/commonTags.tld" %>

<style>
  /* Footer divider */
    .b-example-divider {
      height: 3rem;
    /*background-color: rgba(0, 0, 0, .1);*/
    border: solid rgba(0, 0, 0, .15);
    border-top-width: medium;
    border-right-width: medium;
    border-bottom-width: medium;
    border-left-width: medium;
    border-width: 1px 0;
    box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}
</style>

<div class="b-example-divider"></div>
<div class="container">
  <%-- <footer class="py-5">
  <div class="row">
    <div class="col-6 col-md-2 mb-3">
      <h5>Section</h5>
      <ul class="nav flex-column">
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Home</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Features</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Pricing</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">FAQs</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">About</a></li>
      </ul>
    </div>

    <div class="col-6 col-md-2 mb-3">
      <h5>Section</h5>
      <ul class="nav flex-column">
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Home</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Features</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Pricing</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">FAQs</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">About</a></li>
      </ul>
    </div>

    <div class="col-6 col-md-2 mb-3">
      <h5>Section</h5>
      <ul class="nav flex-column">
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Home</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Features</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Pricing</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">FAQs</a></li>
        <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">About</a></li>
      </ul>
    </div>

    <div class="col-md-5 offset-md-1 mb-3">
      <form>
        <h5>Subscribe to our newsletter</h5>
        <p>Monthly digest of what's new and exciting from us.</p>
        <div class="d-flex flex-column flex-sm-row w-100 gap-2">
          <label for="newsletter1" class="visually-hidden">Email address</label>
          <input id="newsletter1" type="text" class="form-control" placeholder="Email address">
          <button class="btn btn-primary" type="button">Subscribe</button>
        </div>
      </form>
    </div>
  </div>
--%>
  <div class="d-flex flex-column flex-sm-row justify-content-between py-4 my-4 border-top">
    <p>Ros <commonTags:currentYear /> Company, Inc. All rights reserved.</p>
    <ul class="list-unstyled d-flex">
      <li class="ms-3"><a class="link-dark" href="#"><i class="bi bi-twitter"><use xlink:href="#twitter"></use></i></a></li>
      <li class="ms-3"><a class="link-dark" href="#"><i class="bi bi-instagram"><use xlink:href="#instagram"></use></i></a></li>
      <li class="ms-3"><a class="link-dark" href="#"><i class="bi bi-facebook"><use xlink:href="#facebook"></use></i></a></li>
    </ul>
  </div>
</footer>
</div>