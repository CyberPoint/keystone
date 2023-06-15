"use strict";
(self["webpackChunkkey_stone"] = self["webpackChunkkey_stone"] || []).push([["account"],{

/***/ "./src/main/webapp/app/modules/account/index.tsx":
/*!*******************************************************!*\
  !*** ./src/main/webapp/app/modules/account/index.tsx ***!
  \*******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ "./node_modules/react/index.js");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react_router_dom__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! react-router-dom */ "./node_modules/react-router/dist/index.js");
/* harmony import */ var app_shared_error_error_boundary_routes__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! app/shared/error/error-boundary-routes */ "./src/main/webapp/app/shared/error/error-boundary-routes.tsx");
/* harmony import */ var _settings_settings__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./settings/settings */ "./src/main/webapp/app/modules/account/settings/settings.tsx");
/* harmony import */ var _password_password__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./password/password */ "./src/main/webapp/app/modules/account/password/password.tsx");





const AccountRoutes = () => (react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", null,
    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(app_shared_error_error_boundary_routes__WEBPACK_IMPORTED_MODULE_1__["default"], null,
        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_4__.Route, { path: "settings", element: react__WEBPACK_IMPORTED_MODULE_0___default().createElement(_settings_settings__WEBPACK_IMPORTED_MODULE_2__["default"], null) }),
        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_4__.Route, { path: "password", element: react__WEBPACK_IMPORTED_MODULE_0___default().createElement(_password_password__WEBPACK_IMPORTED_MODULE_3__["default"], null) }))));
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (AccountRoutes);


/***/ }),

/***/ "./src/main/webapp/app/modules/account/password/password.tsx":
/*!*******************************************************************!*\
  !*** ./src/main/webapp/app/modules/account/password/password.tsx ***!
  \*******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   PasswordPage: () => (/* binding */ PasswordPage),
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ "./node_modules/react/index.js");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react_jhipster__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react-jhipster */ "./node_modules/react-jhipster/lib/index.js");
/* harmony import */ var react_jhipster__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react_jhipster__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Row.js");
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Col.js");
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Button.js");
/* harmony import */ var react_toastify__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! react-toastify */ "./node_modules/react-toastify/dist/react-toastify.esm.mjs");
/* harmony import */ var app_config_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/config/store */ "./src/main/webapp/app/config/store.ts");
/* harmony import */ var app_shared_reducers_authentication__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/shared/reducers/authentication */ "./src/main/webapp/app/shared/reducers/authentication.ts");
/* harmony import */ var app_shared_layout_password_password_strength_bar__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! app/shared/layout/password/password-strength-bar */ "./src/main/webapp/app/shared/layout/password/password-strength-bar.tsx");
/* harmony import */ var _password_reducer__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./password.reducer */ "./src/main/webapp/app/modules/account/password/password.reducer.ts");








const PasswordPage = () => {
    const [password, setPassword] = (0,react__WEBPACK_IMPORTED_MODULE_0__.useState)('');
    const dispatch = (0,app_config_store__WEBPACK_IMPORTED_MODULE_3__.useAppDispatch)();
    (0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)(() => {
        dispatch((0,_password_reducer__WEBPACK_IMPORTED_MODULE_6__.reset)());
        dispatch((0,app_shared_reducers_authentication__WEBPACK_IMPORTED_MODULE_4__.getSession)());
        return () => {
            dispatch((0,_password_reducer__WEBPACK_IMPORTED_MODULE_6__.reset)());
        };
    }, []);
    const handleValidSubmit = ({ currentPassword, newPassword }) => {
        dispatch((0,_password_reducer__WEBPACK_IMPORTED_MODULE_6__.savePassword)({ currentPassword, newPassword }));
    };
    const updatePassword = event => setPassword(event.target.value);
    const account = (0,app_config_store__WEBPACK_IMPORTED_MODULE_3__.useAppSelector)(state => state.authentication.account);
    const successMessage = (0,app_config_store__WEBPACK_IMPORTED_MODULE_3__.useAppSelector)(state => state.password.successMessage);
    const errorMessage = (0,app_config_store__WEBPACK_IMPORTED_MODULE_3__.useAppSelector)(state => state.password.errorMessage);
    (0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)(() => {
        if (successMessage) {
            react_toastify__WEBPACK_IMPORTED_MODULE_2__.toast.success((0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)(successMessage));
        }
        else if (errorMessage) {
            react_toastify__WEBPACK_IMPORTED_MODULE_2__.toast.error((0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)(errorMessage));
        }
        dispatch((0,_password_reducer__WEBPACK_IMPORTED_MODULE_6__.reset)());
    }, [successMessage, errorMessage]);
    return (react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", null,
        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_7__["default"], { className: "justify-content-center" },
            react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_8__["default"], { md: "8" },
                react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h2", { id: "password-title" },
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.Translate, { contentKey: "password.title", interpolate: { username: account.login } },
                        "Password for ",
                        account.login)),
                react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedForm, { id: "password-form", onSubmit: handleValidSubmit },
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "currentPassword", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.currentpassword.label'), placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.currentpassword.placeholder'), type: "password", validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.newpassword.required') },
                        }, "data-cy": "currentPassword" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "newPassword", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.newpassword.label'), placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.newpassword.placeholder'), type: "password", validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.newpassword.required') },
                            minLength: { value: 4, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.newpassword.minlength') },
                            maxLength: { value: 50, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.newpassword.maxlength') },
                        }, onChange: updatePassword, "data-cy": "newPassword" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(app_shared_layout_password_password_strength_bar__WEBPACK_IMPORTED_MODULE_5__["default"], { password: password }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "confirmPassword", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.confirmpassword.label'), placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.confirmpassword.placeholder'), type: "password", validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.confirmpassword.required') },
                            minLength: { value: 4, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.confirmpassword.minlength') },
                            maxLength: { value: 50, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.confirmpassword.maxlength') },
                            validate: v => v === password || (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.error.dontmatch'),
                        }, "data-cy": "confirmPassword" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_9__["default"], { color: "success", type: "submit", "data-cy": "submit" },
                        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.Translate, { contentKey: "password.form.button" }, "Save")))))));
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (PasswordPage);


/***/ }),

/***/ "./src/main/webapp/app/modules/account/settings/settings.tsx":
/*!*******************************************************************!*\
  !*** ./src/main/webapp/app/modules/account/settings/settings.tsx ***!
  \*******************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   SettingsPage: () => (/* binding */ SettingsPage),
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ "./node_modules/react/index.js");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Row.js");
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Col.js");
/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! reactstrap */ "./node_modules/reactstrap/esm/Button.js");
/* harmony import */ var react_jhipster__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react-jhipster */ "./node_modules/react-jhipster/lib/index.js");
/* harmony import */ var react_jhipster__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react_jhipster__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var react_toastify__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! react-toastify */ "./node_modules/react-toastify/dist/react-toastify.esm.mjs");
/* harmony import */ var app_config_translation__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/config/translation */ "./src/main/webapp/app/config/translation.ts");
/* harmony import */ var app_config_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/config/store */ "./src/main/webapp/app/config/store.ts");
/* harmony import */ var app_shared_reducers_authentication__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! app/shared/reducers/authentication */ "./src/main/webapp/app/shared/reducers/authentication.ts");
/* harmony import */ var _settings_reducer__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./settings.reducer */ "./src/main/webapp/app/modules/account/settings/settings.reducer.ts");








const SettingsPage = () => {
    const dispatch = (0,app_config_store__WEBPACK_IMPORTED_MODULE_4__.useAppDispatch)();
    const account = (0,app_config_store__WEBPACK_IMPORTED_MODULE_4__.useAppSelector)(state => state.authentication.account);
    const successMessage = (0,app_config_store__WEBPACK_IMPORTED_MODULE_4__.useAppSelector)(state => state.settings.successMessage);
    (0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)(() => {
        dispatch((0,app_shared_reducers_authentication__WEBPACK_IMPORTED_MODULE_5__.getSession)());
        return () => {
            dispatch((0,_settings_reducer__WEBPACK_IMPORTED_MODULE_6__.reset)());
        };
    }, []);
    (0,react__WEBPACK_IMPORTED_MODULE_0__.useEffect)(() => {
        if (successMessage) {
            react_toastify__WEBPACK_IMPORTED_MODULE_2__.toast.success((0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)(successMessage));
        }
    }, [successMessage]);
    const handleValidSubmit = values => {
        dispatch((0,_settings_reducer__WEBPACK_IMPORTED_MODULE_6__.saveAccountSettings)(Object.assign(Object.assign({}, account), values)));
    };
    return (react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", null,
        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_7__["default"], { className: "justify-content-center" },
            react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_8__["default"], { md: "8" },
                react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h2", { id: "settings-title" },
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.Translate, { contentKey: "settings.title", interpolate: { username: account.login } },
                        "User settings for ",
                        account.login)),
                react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedForm, { id: "settings-form", onSubmit: handleValidSubmit, defaultValues: account },
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "firstName", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.form.firstname'), id: "firstName", placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.form.firstname.placeholder'), validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.firstname.required') },
                            minLength: { value: 1, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.firstname.minlength') },
                            maxLength: { value: 50, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.firstname.maxlength') },
                        }, "data-cy": "firstname" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "lastName", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.form.lastname'), id: "lastName", placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.form.lastname.placeholder'), validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.lastname.required') },
                            minLength: { value: 1, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.lastname.minlength') },
                            maxLength: { value: 50, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.messages.validate.lastname.maxlength') },
                        }, "data-cy": "lastname" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { name: "email", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.email.label'), placeholder: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.form.email.placeholder'), type: "email", validate: {
                            required: { value: true, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.email.required') },
                            minLength: { value: 5, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.email.minlength') },
                            maxLength: { value: 254, message: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.email.maxlength') },
                            validate: v => (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.isEmail)(v) || (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('global.messages.validate.email.invalid'),
                        }, "data-cy": "email" }),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.ValidatedField, { type: "select", id: "langKey", name: "langKey", label: (0,react_jhipster__WEBPACK_IMPORTED_MODULE_1__.translate)('settings.form.language'), "data-cy": "langKey" }, app_config_translation__WEBPACK_IMPORTED_MODULE_3__.locales.map(locale => (react__WEBPACK_IMPORTED_MODULE_0___default().createElement("option", { value: locale, key: locale }, app_config_translation__WEBPACK_IMPORTED_MODULE_3__.languages[locale].name)))),
                    react__WEBPACK_IMPORTED_MODULE_0___default().createElement(reactstrap__WEBPACK_IMPORTED_MODULE_9__["default"], { color: "primary", type: "submit", "data-cy": "submit" },
                        react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_jhipster__WEBPACK_IMPORTED_MODULE_1__.Translate, { contentKey: "settings.form.button" }, "Save")))))));
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (SettingsPage);


/***/ })

}]);
//# sourceMappingURL=account.08aeccc3.chunk.js.map