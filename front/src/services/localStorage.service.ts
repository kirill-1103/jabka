const TOKEN_KEY = "jwt-token";
const USERID_KEY = "user-local-id";

interface IToken {
  idToken: string;
  localId: number;
}

export function setTokens({ idToken, localId }: IToken) {
  localStorage.setItem(USERID_KEY, String(localId));
  localStorage.setItem(TOKEN_KEY, idToken);
}
export function getAccessToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function removeAuthData() {
  localStorage.removeItem(USERID_KEY);
  localStorage.removeItem(TOKEN_KEY);
}

export function getUserId() {
  return localStorage.getItem(USERID_KEY);
}

const localStorageService = {
  setTokens,
  getAccessToken,
  getUserId,
  removeAuthData,
};
export default localStorageService;
