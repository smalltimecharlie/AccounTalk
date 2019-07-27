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
import { getEntity, updateEntity, createEntity, reset } from './earnings.reducer';
import { IEarnings } from 'app/shared/model/earnings.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEarningsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEarningsUpdateState {
  isNew: boolean;
  employmentDetailsId: string;
}

export class EarningsUpdate extends React.Component<IEarningsUpdateProps, IEarningsUpdateState> {
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
      const { earningsEntity } = this.props;
      const entity = {
        ...earningsEntity,
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
    this.props.history.push('/entity/earnings');
  };

  render() {
    const { earningsEntity, employmentDetails, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.earnings.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.earnings.home.createOrEditLabel">Create or edit a Earnings</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : earningsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="earnings-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="earnings-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="grossPayLabel" for="earnings-grossPay">
                    <Translate contentKey="accounTalkApp.earnings.grossPay">Gross Pay</Translate>
                  </Label>
                  <AvField id="earnings-grossPay" type="string" className="form-control" name="grossPay" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxDeductedLabel" for="earnings-taxDeducted">
                    <Translate contentKey="accounTalkApp.earnings.taxDeducted">Tax Deducted</Translate>
                  </Label>
                  <AvField id="earnings-taxDeducted" type="string" className="form-control" name="taxDeducted" />
                </AvGroup>
                <AvGroup>
                  <Label id="studentLoanDeductionsLabel" for="earnings-studentLoanDeductions">
                    <Translate contentKey="accounTalkApp.earnings.studentLoanDeductions">Student Loan Deductions</Translate>
                  </Label>
                  <AvField id="earnings-studentLoanDeductions" type="string" className="form-control" name="studentLoanDeductions" />
                </AvGroup>
                <AvGroup>
                  <Label for="earnings-employmentDetails">
                    <Translate contentKey="accounTalkApp.earnings.employmentDetails">Employment Details</Translate>
                  </Label>
                  <AvInput id="earnings-employmentDetails" type="select" className="form-control" name="employmentDetails.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/earnings" replace color="info">
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
  earningsEntity: storeState.earnings.entity,
  loading: storeState.earnings.loading,
  updating: storeState.earnings.updating,
  updateSuccess: storeState.earnings.updateSuccess
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
)(EarningsUpdate);
