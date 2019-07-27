import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPensionReceived, defaultValue } from 'app/shared/model/pension-received.model';

export const ACTION_TYPES = {
  FETCH_PENSIONRECEIVED_LIST: 'pensionReceived/FETCH_PENSIONRECEIVED_LIST',
  FETCH_PENSIONRECEIVED: 'pensionReceived/FETCH_PENSIONRECEIVED',
  CREATE_PENSIONRECEIVED: 'pensionReceived/CREATE_PENSIONRECEIVED',
  UPDATE_PENSIONRECEIVED: 'pensionReceived/UPDATE_PENSIONRECEIVED',
  DELETE_PENSIONRECEIVED: 'pensionReceived/DELETE_PENSIONRECEIVED',
  RESET: 'pensionReceived/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPensionReceived>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PensionReceivedState = Readonly<typeof initialState>;

// Reducer

export default (state: PensionReceivedState = initialState, action): PensionReceivedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PENSIONRECEIVED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PENSIONRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PENSIONRECEIVED):
    case REQUEST(ACTION_TYPES.UPDATE_PENSIONRECEIVED):
    case REQUEST(ACTION_TYPES.DELETE_PENSIONRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PENSIONRECEIVED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.CREATE_PENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.UPDATE_PENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.DELETE_PENSIONRECEIVED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PENSIONRECEIVED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PENSIONRECEIVED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PENSIONRECEIVED):
    case SUCCESS(ACTION_TYPES.UPDATE_PENSIONRECEIVED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PENSIONRECEIVED):
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

const apiUrl = 'api/pension-receiveds';

// Actions

export const getEntities: ICrudGetAllAction<IPensionReceived> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PENSIONRECEIVED_LIST,
  payload: axios.get<IPensionReceived>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPensionReceived> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PENSIONRECEIVED,
    payload: axios.get<IPensionReceived>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPensionReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PENSIONRECEIVED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPensionReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PENSIONRECEIVED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPensionReceived> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PENSIONRECEIVED,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
