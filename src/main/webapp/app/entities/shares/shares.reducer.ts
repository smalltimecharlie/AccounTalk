import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShares, defaultValue } from 'app/shared/model/shares.model';

export const ACTION_TYPES = {
  FETCH_SHARES_LIST: 'shares/FETCH_SHARES_LIST',
  FETCH_SHARES: 'shares/FETCH_SHARES',
  CREATE_SHARES: 'shares/CREATE_SHARES',
  UPDATE_SHARES: 'shares/UPDATE_SHARES',
  DELETE_SHARES: 'shares/DELETE_SHARES',
  RESET: 'shares/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShares>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SharesState = Readonly<typeof initialState>;

// Reducer

export default (state: SharesState = initialState, action): SharesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SHARES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHARES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHARES):
    case REQUEST(ACTION_TYPES.UPDATE_SHARES):
    case REQUEST(ACTION_TYPES.DELETE_SHARES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SHARES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHARES):
    case FAILURE(ACTION_TYPES.CREATE_SHARES):
    case FAILURE(ACTION_TYPES.UPDATE_SHARES):
    case FAILURE(ACTION_TYPES.DELETE_SHARES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHARES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHARES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHARES):
    case SUCCESS(ACTION_TYPES.UPDATE_SHARES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHARES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shares';

// Actions

export const getEntities: ICrudGetAllAction<IShares> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHARES_LIST,
  payload: axios.get<IShares>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IShares> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHARES,
    payload: axios.get<IShares>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShares> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHARES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShares> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHARES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShares> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHARES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
