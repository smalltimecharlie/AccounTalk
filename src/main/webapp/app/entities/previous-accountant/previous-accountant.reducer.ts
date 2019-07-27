import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPreviousAccountant, defaultValue } from 'app/shared/model/previous-accountant.model';

export const ACTION_TYPES = {
  FETCH_PREVIOUSACCOUNTANT_LIST: 'previousAccountant/FETCH_PREVIOUSACCOUNTANT_LIST',
  FETCH_PREVIOUSACCOUNTANT: 'previousAccountant/FETCH_PREVIOUSACCOUNTANT',
  CREATE_PREVIOUSACCOUNTANT: 'previousAccountant/CREATE_PREVIOUSACCOUNTANT',
  UPDATE_PREVIOUSACCOUNTANT: 'previousAccountant/UPDATE_PREVIOUSACCOUNTANT',
  DELETE_PREVIOUSACCOUNTANT: 'previousAccountant/DELETE_PREVIOUSACCOUNTANT',
  RESET: 'previousAccountant/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPreviousAccountant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PreviousAccountantState = Readonly<typeof initialState>;

// Reducer

export default (state: PreviousAccountantState = initialState, action): PreviousAccountantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PREVIOUSACCOUNTANT):
    case REQUEST(ACTION_TYPES.UPDATE_PREVIOUSACCOUNTANT):
    case REQUEST(ACTION_TYPES.DELETE_PREVIOUSACCOUNTANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT):
    case FAILURE(ACTION_TYPES.CREATE_PREVIOUSACCOUNTANT):
    case FAILURE(ACTION_TYPES.UPDATE_PREVIOUSACCOUNTANT):
    case FAILURE(ACTION_TYPES.DELETE_PREVIOUSACCOUNTANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PREVIOUSACCOUNTANT):
    case SUCCESS(ACTION_TYPES.UPDATE_PREVIOUSACCOUNTANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PREVIOUSACCOUNTANT):
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

const apiUrl = 'api/previous-accountants';

// Actions

export const getEntities: ICrudGetAllAction<IPreviousAccountant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT_LIST,
  payload: axios.get<IPreviousAccountant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPreviousAccountant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PREVIOUSACCOUNTANT,
    payload: axios.get<IPreviousAccountant>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPreviousAccountant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PREVIOUSACCOUNTANT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPreviousAccountant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PREVIOUSACCOUNTANT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPreviousAccountant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PREVIOUSACCOUNTANT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
