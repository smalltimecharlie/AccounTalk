import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDividendsReceived, defaultValue } from 'app/shared/model/dividends-received.model';

export const ACTION_TYPES = {
  FETCH_DIVIDENDSRECEIVED_LIST: 'dividendsReceived/FETCH_DIVIDENDSRECEIVED_LIST',
  FETCH_DIVIDENDSRECEIVED: 'dividendsReceived/FETCH_DIVIDENDSRECEIVED',
  CREATE_DIVIDENDSRECEIVED: 'dividendsReceived/CREATE_DIVIDENDSRECEIVED',
  UPDATE_DIVIDENDSRECEIVED: 'dividendsReceived/UPDATE_DIVIDENDSRECEIVED',
  DELETE_DIVIDENDSRECEIVED: 'dividendsReceived/DELETE_DIVIDENDSRECEIVED',
  RESET: 'dividendsReceived/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDividendsReceived>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DividendsReceivedState = Readonly<typeof initialState>;

// Reducer

export default (state: DividendsReceivedState = initialState, action): DividendsReceivedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DIVIDENDSRECEIVED):
    case REQUEST(ACTION_TYPES.UPDATE_DIVIDENDSRECEIVED):
    case REQUEST(ACTION_TYPES.DELETE_DIVIDENDSRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED):
    case FAILURE(ACTION_TYPES.CREATE_DIVIDENDSRECEIVED):
    case FAILURE(ACTION_TYPES.UPDATE_DIVIDENDSRECEIVED):
    case FAILURE(ACTION_TYPES.DELETE_DIVIDENDSRECEIVED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DIVIDENDSRECEIVED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DIVIDENDSRECEIVED):
    case SUCCESS(ACTION_TYPES.UPDATE_DIVIDENDSRECEIVED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DIVIDENDSRECEIVED):
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

const apiUrl = 'api/dividends-receiveds';

// Actions

export const getEntities: ICrudGetAllAction<IDividendsReceived> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DIVIDENDSRECEIVED_LIST,
  payload: axios.get<IDividendsReceived>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDividendsReceived> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DIVIDENDSRECEIVED,
    payload: axios.get<IDividendsReceived>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDividendsReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DIVIDENDSRECEIVED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDividendsReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DIVIDENDSRECEIVED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDividendsReceived> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DIVIDENDSRECEIVED,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
