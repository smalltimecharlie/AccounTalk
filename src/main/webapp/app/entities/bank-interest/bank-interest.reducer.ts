import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBankInterest, defaultValue } from 'app/shared/model/bank-interest.model';

export const ACTION_TYPES = {
  FETCH_BANKINTEREST_LIST: 'bankInterest/FETCH_BANKINTEREST_LIST',
  FETCH_BANKINTEREST: 'bankInterest/FETCH_BANKINTEREST',
  CREATE_BANKINTEREST: 'bankInterest/CREATE_BANKINTEREST',
  UPDATE_BANKINTEREST: 'bankInterest/UPDATE_BANKINTEREST',
  DELETE_BANKINTEREST: 'bankInterest/DELETE_BANKINTEREST',
  RESET: 'bankInterest/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBankInterest>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BankInterestState = Readonly<typeof initialState>;

// Reducer

export default (state: BankInterestState = initialState, action): BankInterestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BANKINTEREST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANKINTEREST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BANKINTEREST):
    case REQUEST(ACTION_TYPES.UPDATE_BANKINTEREST):
    case REQUEST(ACTION_TYPES.DELETE_BANKINTEREST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BANKINTEREST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANKINTEREST):
    case FAILURE(ACTION_TYPES.CREATE_BANKINTEREST):
    case FAILURE(ACTION_TYPES.UPDATE_BANKINTEREST):
    case FAILURE(ACTION_TYPES.DELETE_BANKINTEREST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKINTEREST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKINTEREST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANKINTEREST):
    case SUCCESS(ACTION_TYPES.UPDATE_BANKINTEREST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANKINTEREST):
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

const apiUrl = 'api/bank-interests';

// Actions

export const getEntities: ICrudGetAllAction<IBankInterest> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BANKINTEREST_LIST,
  payload: axios.get<IBankInterest>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBankInterest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANKINTEREST,
    payload: axios.get<IBankInterest>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBankInterest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANKINTEREST,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBankInterest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANKINTEREST,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBankInterest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANKINTEREST,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
