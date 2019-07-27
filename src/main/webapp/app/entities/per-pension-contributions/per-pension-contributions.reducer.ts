import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPerPensionContributions, defaultValue } from 'app/shared/model/per-pension-contributions.model';

export const ACTION_TYPES = {
  FETCH_PERPENSIONCONTRIBUTIONS_LIST: 'perPensionContributions/FETCH_PERPENSIONCONTRIBUTIONS_LIST',
  FETCH_PERPENSIONCONTRIBUTIONS: 'perPensionContributions/FETCH_PERPENSIONCONTRIBUTIONS',
  CREATE_PERPENSIONCONTRIBUTIONS: 'perPensionContributions/CREATE_PERPENSIONCONTRIBUTIONS',
  UPDATE_PERPENSIONCONTRIBUTIONS: 'perPensionContributions/UPDATE_PERPENSIONCONTRIBUTIONS',
  DELETE_PERPENSIONCONTRIBUTIONS: 'perPensionContributions/DELETE_PERPENSIONCONTRIBUTIONS',
  RESET: 'perPensionContributions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPerPensionContributions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PerPensionContributionsState = Readonly<typeof initialState>;

// Reducer

export default (state: PerPensionContributionsState = initialState, action): PerPensionContributionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PERPENSIONCONTRIBUTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_PERPENSIONCONTRIBUTIONS):
    case REQUEST(ACTION_TYPES.DELETE_PERPENSIONCONTRIBUTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.CREATE_PERPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_PERPENSIONCONTRIBUTIONS):
    case FAILURE(ACTION_TYPES.DELETE_PERPENSIONCONTRIBUTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERPENSIONCONTRIBUTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_PERPENSIONCONTRIBUTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERPENSIONCONTRIBUTIONS):
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

const apiUrl = 'api/per-pension-contributions';

// Actions

export const getEntities: ICrudGetAllAction<IPerPensionContributions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS_LIST,
  payload: axios.get<IPerPensionContributions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPerPensionContributions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERPENSIONCONTRIBUTIONS,
    payload: axios.get<IPerPensionContributions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPerPensionContributions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERPENSIONCONTRIBUTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPerPensionContributions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERPENSIONCONTRIBUTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPerPensionContributions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERPENSIONCONTRIBUTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
