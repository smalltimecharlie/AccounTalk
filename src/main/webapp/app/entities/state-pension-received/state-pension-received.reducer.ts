import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStatePensionReceived, defaultValue } from 'app/shared/model/state-pension-received.model';

export const ACTION_TYPES = {
  FETCH_STATEPENSIONRECEIVED_LIST: 'statePensionReceived/FETCH_STATEPENSIONRECEIVED_LIST',
  FETCH_STATEPENSIONRECEIVED: 'statePensionReceived/FETCH_STATEPENSIONRECEIVED',
  CREATE_STATEPENSIONRECEIVED: 'statePensionReceived/CREATE_STATEPENSIONRECEIVED',
  UPDATE_STATEPENSIONRECEIVED: 'statePensionReceived/UPDATE_STATEPENSIONRECEIVED',
  DELETE_STATEPENSIONRECEIVED: 'statePensionReceived/DELETE_STATEPENSIONRECEIVED',
  RESET: 'statePensionReceived/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStatePensionReceived>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StatePensionReceivedState = Readonly<typeof initialState>;

// Reducer

export default (state: StatePensionReceivedState = initialState, action): StatePensionReceivedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STATEPENSIONRECEIVED):
    case REQUEST(ACTION_TYPES.UPDATE_STATEPENSIONRECEIVED):
    case REQUEST(ACTION_TYPES.DELETE_STATEPENSIONRECEIVED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.CREATE_STATEPENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.UPDATE_STATEPENSIONRECEIVED):
    case FAILURE(ACTION_TYPES.DELETE_STATEPENSIONRECEIVED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STATEPENSIONRECEIVED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STATEPENSIONRECEIVED):
    case SUCCESS(ACTION_TYPES.UPDATE_STATEPENSIONRECEIVED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STATEPENSIONRECEIVED):
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

const apiUrl = 'api/state-pension-receiveds';

// Actions

export const getEntities: ICrudGetAllAction<IStatePensionReceived> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STATEPENSIONRECEIVED_LIST,
  payload: axios.get<IStatePensionReceived>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IStatePensionReceived> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STATEPENSIONRECEIVED,
    payload: axios.get<IStatePensionReceived>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStatePensionReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STATEPENSIONRECEIVED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStatePensionReceived> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STATEPENSIONRECEIVED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStatePensionReceived> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STATEPENSIONRECEIVED,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
