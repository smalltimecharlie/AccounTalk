import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmpPensionContributions, defaultValue } from 'app/shared/model/emp-pension-contributions.model';

export const ACTION_TYPES = {
  FETCH_EMPPENSIONCONTRIBUTIONS_LIST: 'empPensionContributions/FETCH_EMPPENSIONCONTRIBUTIONS_LIST',
  FETCH_EMPPENSIONCONTRIBUTIONS: 'empPensionContributions/FETCH_EMPPENSIONCONTRIBUTIONS',
  CREATE_EMPPENSIONCONTRIBUTIONS: 'empPensionContributions/CREATE_EMPPENSIONCONTRIBUTIONS',
  UPDATE_EMPPENSIONCONTRIBUTIONS: 'empPensionContributions/UPDATE_EMPPENSIONCONTRIBUTIONS',
  DELETE_EMPPENSIONCONTRIBUTIONS: 'empPensionContributions/DELETE_EMPPENSIONCONTRIBUTIONS',
  RESET: 'empPensionContributions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmpPensionContributions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EmpPensionContributionsState = Readonly<typeof initialState>;

// Reducer

export default (state: EmpPensionContributionsState = initialState, action): EmpPensionContributionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPPENSIONCONTRIBUTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_EMPPENSIONCONTRIBUTIONS):
    case REQUEST(ACTION_TYPES.DELETE_EMPPENSIONCONTRIBUTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.CREATE_EMPPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_EMPPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.DELETE_EMPPENSIONCONTRIBUTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPPENSIONCONTRIBUTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPPENSIONCONTRIBUTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPPENSIONCONTRIBUTIONS):
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

const apiUrl = 'api/emp-pension-contributions';

// Actions

export const getEntities: ICrudGetAllAction<IEmpPensionContributions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS_LIST,
  payload: axios.get<IEmpPensionContributions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEmpPensionContributions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPPENSIONCONTRIBUTIONS,
    payload: axios.get<IEmpPensionContributions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmpPensionContributions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPPENSIONCONTRIBUTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmpPensionContributions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPPENSIONCONTRIBUTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmpPensionContributions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPPENSIONCONTRIBUTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
