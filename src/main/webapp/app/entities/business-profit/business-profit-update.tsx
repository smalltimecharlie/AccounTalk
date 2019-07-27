import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { IBusiness } from 'app/shared/model/business.model';
import { getEntities as getBusinesses } from 'app/entities/business/business.reducer';
import { getEntity, updateEntity, createEntity, reset } from './business-profit.reducer';
import { IBusinessProfit } from 'app/shared/model/business-profit.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBusinessProfitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBusinessProfitUpdateState {
  isNew: boolean;
  taxReturnId: string;
  businessId: string;
}

export class BusinessProfitUpdate extends React.Component<IBusinessProfitUpdateProps, IBusinessProfitUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
      businessId: '0',
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

    this.props.getTaxReturns();
    this.props.getBusinesses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { businessProfitEntity } = this.props;
      const entity = {
        ...businessProfitEntity,
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
    this.props.history.push('/entity/business-profit');
  };

  render() {
    const { businessProfitEntity, taxReturns, businesses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.businessProfit.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.businessProfit.home.createOrEditLabel">Create or edit a BusinessProfit</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : businessProfitEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="business-profit-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="business-profit-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="turnoverLabel" for="business-profit-turnover">
                    <Translate contentKey="accounTalkApp.businessProfit.turnover">Turnover</Translate>
                  </Label>
                  <AvField id="business-profit-turnover" type="string" className="form-control" name="turnover" />
                </AvGroup>
                <AvGroup>
                  <Label id="expensesLabel" for="business-profit-expenses">
                    <Translate contentKey="accounTalkApp.businessProfit.expenses">Expenses</Translate>
                  </Label>
                  <AvField id="business-profit-expenses" type="string" className="form-control" name="expenses" />
                </AvGroup>
                <AvGroup>
                  <Label id="capitalAllowancesLabel" for="business-profit-capitalAllowances">
                    <Translate contentKey="accounTalkApp.businessProfit.capitalAllowances">Capital Allowances</Translate>
                  </Label>
                  <AvField id="business-profit-capitalAllowances" type="string" className="form-control" name="capitalAllowances" />
                </AvGroup>
                <AvGroup>
                  <Label id="profitLabel" for="business-profit-profit">
                    <Translate contentKey="accounTalkApp.businessProfit.profit">Profit</Translate>
                  </Label>
                  <AvField id="business-profit-profit" type="string" className="form-control" name="profit" />
                </AvGroup>
                <AvGroup>
                  <Label id="cisTaxDeductedLabel" for="business-profit-cisTaxDeducted">
                    <Translate contentKey="accounTalkApp.businessProfit.cisTaxDeducted">Cis Tax Deducted</Translate>
                  </Label>
                  <AvField id="business-profit-cisTaxDeducted" type="string" className="form-control" name="cisTaxDeducted" />
                </AvGroup>
                <AvGroup>
                  <Label for="business-profit-taxReturn">
                    <Translate contentKey="accounTalkApp.businessProfit.taxReturn">Tax Return</Translate>
                  </Label>
                  <AvInput id="business-profit-taxReturn" type="select" className="form-control" name="taxReturn.id">
                    <option value="" key="0" />
                    {taxReturns
                      ? taxReturns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="business-profit-business">
                    <Translate contentKey="accounTalkApp.businessProfit.business">Business</Translate>
                  </Label>
                  <AvInput id="business-profit-business" type="select" className="form-control" name="business.id">
                    <option value="" key="0" />
                    {businesses
                      ? businesses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/business-profit" replace color="info">
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
  taxReturns: storeState.taxReturn.entities,
  businesses: storeState.business.entities,
  businessProfitEntity: storeState.businessProfit.entity,
  loading: storeState.businessProfit.loading,
  updating: storeState.businessProfit.updating,
  updateSuccess: storeState.businessProfit.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
  getBusinesses,
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
)(BusinessProfitUpdate);
