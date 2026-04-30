import axios from "axios"
import {getToken, removeToken} from "./utils/token-auth";

export const SUCCESS_CODE = 10000;
export const NOT_LOGIN_CODE = 30001;
export const TOKEN_EXPIRED_CODE = 30002;
export const TOKEN_INVALID_CODE = 30003;
export const USERNAME_ERROR_CODE = 31001;
export const PASSWORD_ERROR_CODE = 31002;
export const USERNAME_USED_CODE = 31003;
export const HEADLINE_NO_PERMISSION_CODE = 40002;

const authErrorCodes = new Set([
	NOT_LOGIN_CODE,
	TOKEN_EXPIRED_CODE,
	TOKEN_INVALID_CODE
]);

function parseResponseData(data: string) {
	if (typeof data !== "string" || data.length === 0) {
		return data;
	}

	try {
		// 后端使用雪花 Long ID，超过 JS 安全整数范围时要先转成字符串再解析。
		const safeData = data.replace(/("(?:hid|uid|publisher)"\s*:\s*)(\d{16,})/g, '$1"$2"');
		return JSON.parse(safeData);
	} catch {
		return data;
	}
}

const instance = axios.create({
		baseURL: "/api",
		timeout: 5000,
		transformResponse: [parseResponseData]
	}
)
instance.interceptors.request.use(
	(config) => {
		config.headers.set("Accept", "application/json,text/plain,text/html");
		const token = getToken();
		if(token){
			config.headers.set("Authorization",`Bearer ${token}`);
		}
		return config;
	}, (error) => {
		return Promise.reject(error);
	}
)

instance.interceptors.response.use(
	(response) => {
		const code = response?.data?.code;
		if (authErrorCodes.has(code)) {
			removeToken();
			if (window.location.pathname !== "/login") {
				window.location.href = "/login";
			}
		}
		return response;
	}, (error) => {
		const code = error?.response?.data?.code;
		if(authErrorCodes.has(code)){
			removeToken();
			window.location.href = "/login";
		}
		return Promise.reject(error);
	}
)

export default instance;
