import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
import { getEntities as getEmploymentDetails } from 'app/entities/employment-details/employment-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './expenses.reducer';
import { IExpenses } from 'app/shared/model/expenses.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExpensesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IExpensesUpdateState {
  isNew: boolean;
  employmentDetailsId: string;
}

export class ExpensesUpdate extends React.Component<IExpensesUpdateProps, IExpensesUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      employmentDetailsId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEmploymentDetails();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { expensesEntity } = this.props;
      const entity = {
        ...expensesEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/expenses');
  };

  render() {
    const { expensesEntity, employmentDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.expenses.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.expenses.home.createOrEditLabel">Create or edit a Expenses</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : expensesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="expenses-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="expenses-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="expenseTypeLabel" for="expenses-expenseType">
                    <Translate contentKey="accounTalkApp.expenses.expenseType">Expense Type</Translate>
                  </Label>
                  <AvInput
                    id="expenses-expenseType"
                    type="select"
                    className="form-control"
                    name="expenseType"
                    value={(!isNew && expensesEntity.expenseType) || 'UNSPECIFIED'}
                  >
                    <option value="UNSPECIFIED">{translate('accounTalkApp.ExpenseType.UNSPECIFIED')}</option>
                    <option value="SUBSCRIPTIONS">{translate('accounTalkApp.ExpenseType.SUBSCRIPTIONS')}</option>
                    <option value="MOTOR_EXPENSE_IN_OWN_CAR">{translate('accounTalkApp.ExpenseType.MOTOR_EXPENSE_IN_OWN_CAR')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="expenses-description">
                    <Translate contentKey="accounTalkApp.expenses.description">Description</Translate>
                  </Label>
                  <AvField id="expenses-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="valueLabel" for="expenses-value">
                    <Translate contentKey="accounTalkApp.expenses.value">Value</Translate>
                  </Label>
                  <AvField id="expenses-value" type="string" className="form-control" name="value" />
                </AvGroup>
                <AvGroup>
                  <Label for="expenses-employmentDetails">
                    <Translate contentKey="accounTalkApp.expenses.employmentDetails">Employment Details</Translate>
                  </Label>
                  <AvInput id="expenses-employmentDetails" type="select" className="form-control" name="employmentDetails.id">
                    <option value="" key="0" />
                    {employmentDetails
                      ? employmentDetails.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/expenses" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  employmentDetails: storeState.employmentDetails.entities,
  expensesEntity: storeState.expenses.entity,
  loading: storeState.expenses.loading,
  updating: storeState.expenses.updating,
  updateSuccess: storeState.expenses.updateSuccess
});

const mapDispatchToProps = {
  getEmploymentDetails,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExpensesUpdate);
