import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEarnings, defaultValue } from 'app/shared/model/earnings.model';

export const ACTION_TYPES = {
  FETCH_EARNINGS_LIST: 'earnings/FETCH_EARNINGS_LIST',
  FETCH_EARNINGS: 'earnings/FETCH_EARNINGS',
  CREATE_EARNINGS: 'earnings/CREATE_EARNINGS',
  UPDATE_EARNINGS: 'earnings/UPDATE_EARNINGS',
  DELETE_EARNINGS: 'earnings/DELETE_EARNINGS',
  RESET: 'earnings/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEarnings>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EarningsState = Readonly<typeof initialState>;

// Reducer

export default (state: EarningsState = initialState, action): EarningsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EARNINGS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EARNINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EARNINGS):
    case REQUEST(ACTION_TYPES.UPDATE_EARNINGS):
    case REQUEST(ACTION_TYPES.DELETE_EARNINGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EARNINGS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EARNINGS):
    case FAILURE(ACTION_TYPES.CREATE_EARNINGS):
    case FAILURE(ACTION_TYPES.UPDATE_EARNINGS):
    case FAILURE(ACTION_TYPES.DELETE_EARNINGS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EARNINGS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EARNINGS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EARNINGS):
    case SUCCESS(ACTION_TYPES.UPDATE_EARNINGS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EARNINGS):
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

const apiUrl = 'api/earnings';

// Actions

export const getEntities: ICrudGetAllAction<IEarnings> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EARNINGS_LIST,
  payload: axios.get<IEarnings>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEarnings> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EARNINGS,
    payload: axios.get<IEarnings>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEarnings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EARNINGS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEarnings> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EARNINGS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEarnings> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EARNINGS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
