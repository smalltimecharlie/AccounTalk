import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBankDetails, defaultValue } from 'app/shared/model/bank-details.model';

export const ACTION_TYPES = {
  FETCH_BANKDETAILS_LIST: 'bankDetails/FETCH_BANKDETAILS_LIST',
  FETCH_BANKDETAILS: 'bankDetails/FETCH_BANKDETAILS',
  CREATE_BANKDETAILS: 'bankDetails/CREATE_BANKDETAILS',
  UPDATE_BANKDETAILS: 'bankDetails/UPDATE_BANKDETAILS',
  DELETE_BANKDETAILS: 'bankDetails/DELETE_BANKDETAILS',
  RESET: 'bankDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBankDetails>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BankDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: BankDetailsState = initialState, action): BankDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BANKDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANKDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BANKDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_BANKDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_BANKDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BANKDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANKDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_BANKDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_BANKDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_BANKDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANKDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_BANKDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANKDETAILS):
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

const apiUrl = 'api/bank-details';

// Actions

export const getEntities: ICrudGetAllAction<IBankDetails> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BANKDETAILS_LIST,
  payload: axios.get<IBankDetails>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBankDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANKDETAILS,
    payload: axios.get<IBankDetails>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBankDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANKDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBankDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANKDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBankDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANKDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
